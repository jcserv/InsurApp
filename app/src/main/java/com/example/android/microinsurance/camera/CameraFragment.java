package com.example.android.microinsurance.camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import org.apache.commons.io.FileUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.android.microinsurance.R;
import com.example.android.microinsurance.camera.model.ImageResponse;
import com.example.android.microinsurance.camera.network.UploadApi;
import com.example.android.microinsurance.camera.network.UploadService;
import com.example.android.microinsurance.common.NetworkBuilder;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static androidx.navigation.fragment.NavHostFragment.findNavController;

//TODO: Clean up needed
public class CameraFragment extends Fragment {

    // This is an arbitrary number for camera permission
    private static final int REQUEST_CODE_PERMISSIONS = 10;
    private static final String APP_TAG = "MICRO_INSURANCE";
    private static final String photoFileName = "capture.jpg";
    private static final int SCALE_WIDTH = 1024;
    private static final int SCALE_HEIGHT = 1024;

    private File photoFile;

    private LinearLayout rootView;
    private ImageView capturePreview;
    private Toolbar cameraToolbar;
    private Button saveButton;
    private EditText toolbarTitle;

    public static CameraFragment newInstance() {
        CameraFragment fragment = new CameraFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(R.layout.fragment_camera, container, false);
        return inflatedView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rootView = view.findViewById(R.id.root_content_frame);
        capturePreview = view.findViewById(R.id.capture_preview);
        cameraToolbar = view.findViewById(R.id.camera_toolbar);
        saveButton = view.findViewById(R.id.save_button);
        saveButton.getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        toolbarTitle = view.findViewById(R.id.result_edit_text);
        saveButton.setOnClickListener((view1)->{
            String content = toolbarTitle.getText().toString();
            Toast.makeText(getActivity().getApplicationContext(),content,Toast.LENGTH_SHORT).show();
        });

        setupToolbar();
        launchCamera();
    }

    private void setupToolbar() {
        cameraToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        cameraToolbar.setNavigationOnClickListener((view) -> {
            findNavController(this).navigate(R.id.action_camera_dest_to_home_dest);
        });
        cameraToolbar.setTitle(R.string.camera_title);
    }

    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);

        Uri fileProvider = FileProvider.getUriForFile(getContext(), getActivity().getApplicationContext().getPackageName() + ".provider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(intent, REQUEST_CODE_PERMISSIONS);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSIONS);
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (resultCode == RESULT_OK) {

                //showRootViews();

                //Rotate Bitmap to correct orientation
                Bitmap orientationCorrectedBitmap = rotateBitmapOrientation(photoFile.getAbsolutePath());

                byte bytes[] = null;
                try {
                    bytes = FileUtils.readFileToByteArray(photoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String encoded = Base64.encodeToString(bytes, Base64.DEFAULT);

                sentImageNetworkRequest(encoded);

                // RESIZE BITMAP
                //Bitmap resizedBitmap = BitmapScaler.scaleToFill(orientationCorrectedBitmap, SCALE_WIDTH, SCALE_HEIGHT);

                // Load the taken image into a preview
                capturePreview.setImageBitmap(orientationCorrectedBitmap);

            } else {
                // User did not take photos, go back to home destination
                findNavController(this).navigate(R.id.action_camera_dest_to_home_dest);
            }
        }
    }

    private void sentImageNetworkRequest(String encoded) {
        UploadApi uploadApi = new NetworkBuilder(getString(R.string.url_image_upload_endpoint)).UseGson().useRxJava2().build(UploadApi.class);
        UploadService uploadService = new UploadService(uploadApi);
        Call<ImageResponse> call = uploadService.uploadImage(encoded);

        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                Toast.makeText(getContext(), "success" + response.body().getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void showRootViews(){
        //show all views
        rootView.setVisibility(View.VISIBLE);
    }

    // From: https://stackoverflow.com/questions/12933085/android-camera-intent-saving-image-landscape-when-taken-portrait/12933632#12933632
    Bitmap rotateBitmapOrientation(String photoFilePath) {
        // Create and configure BitmapFactory
        BitmapFactory.Options bounds = new BitmapFactory.Options();
        bounds.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFilePath, bounds);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);
        // Read EXIF Data
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(photoFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String orientString = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle = 0;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
        if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;
        // Rotate Bitmap
        Matrix matrix = new Matrix();
        matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        // Return result
        return rotatedBitmap;
    }
}
