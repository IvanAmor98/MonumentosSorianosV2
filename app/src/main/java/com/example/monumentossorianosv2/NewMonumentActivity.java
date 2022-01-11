package com.example.monumentossorianosv2;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NewMonumentActivity extends AppCompatActivity {

    private Bitmap bitmap;
    private Bitmap videoBitmap;
    private File image;
    private File video;
    private final ActivityResultLauncher<Intent> activityResultLauncherTake = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    //Intent data = result.getData();
                    //this.bitmap = (Bitmap) data.getExtras().get("data");
                    bitmap = ThumbnailUtils.extractThumbnail(
                            BitmapFactory.decodeFile(image.getAbsolutePath()),
                            80, 80);
                }
            });
    private final ActivityResultLauncher<Intent> activityResultLauncherTakeVideo = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    //Intent data = result.getData();
                    //this.bitmap = (Bitmap) data.getExtras().get("data");
                    videoBitmap = ThumbnailUtils.createVideoThumbnail(
                            video.getAbsolutePath(), MediaStore.Images.Thumbnails.MINI_KIND);
                }
            });
    private final ActivityResultLauncher<Intent> activityResultLauncherLoad = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                if (result.getResultCode() == Activity.RESULT_OK) {
                    InputStream in = null;
                    try {
                        in = this.getContentResolver().openInputStream(data.getData());
                        FileOutputStream out = new FileOutputStream(image);
                        try {
                            byte[] buf = new byte[1024*4];
                            int len;
                            while((len=in.read(buf))>0){
                                out.write(buf,0,len);
                            }
                            bitmap = ThumbnailUtils.extractThumbnail(
                                    BitmapFactory.decodeFile(image.getAbsolutePath()),
                                    80, 80);

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        finally {
                            try {
                                out.close();
                                in.close();
                            }
                            catch ( IOException e ) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

    private final ActivityResultLauncher<Intent> activityResultLauncherLoadVideo = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                if (result.getResultCode() == Activity.RESULT_OK) {
                    InputStream in = null;
                    try {
                        in = this.getContentResolver().openInputStream(data.getData());
                        FileOutputStream out = new FileOutputStream(video);
                        try {
                            byte[] buf = new byte[1024*4];
                            int len;
                            while((len=in.read(buf))>0){
                                out.write(buf,0,len);
                            }
                            videoBitmap = ThumbnailUtils.createVideoThumbnail(
                                    video.getAbsolutePath(), MediaStore.Images.Thumbnails.MINI_KIND);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        finally {
                            try {
                                out.close();
                                in.close();
                                //Para reproducir el video
                                /*
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getAbsolutePath()));
                                intent.setDataAndType(Uri.parse(video.getAbsolutePath()), "video/3gp");
                                startActivity(intent);
                                 */
                            }
                            catch ( IOException e ) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_monument);
    }

    public void saveMonument(View v) {
        MonumentDTO monument = new MonumentDTO(
                ((TextView)findViewById(R.id.nameInput)).getText().toString(),
                MonumentDTO.MonumentType.values()[((Spinner)findViewById(R.id.typeSpinner)).getSelectedItemPosition()],
                ((TextView)findViewById(R.id.addressInput)).getText().toString(),
                Integer.parseInt(((TextView)findViewById(R.id.phoneInput)).getText().toString()),
                ((TextView)findViewById(R.id.urlInput)).getText().toString(),
                ((TextView)findViewById(R.id.commentInput)).getText().toString(),
                this.bitmap,
                this.image.getAbsolutePath()
        );
        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtra("monument", monument);
        startActivity(intent);
        finish();
    }

    public void takePicture(View v) throws IOException {
        String monumentName = ((TextView)findViewById(R.id.nameInput)).getText().toString();
        if (monumentName.equals("")) {
            Toast.makeText(this, "ERROR: Debe introducir antes un nombre", Toast.LENGTH_LONG).show();
            return;
        }

        File pictureDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!pictureDir.exists())
            pictureDir.mkdirs();

        image = File.createTempFile(
                monumentName,
                ".jpg",
                pictureDir
        );

        Uri photoURI = FileProvider.getUriForFile(this,
                "com.example.android.fileprovider",
                image);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        activityResultLauncherTake.launch(takePictureIntent);
    }

    public void takeVideo(View v) throws IOException {
        String monumentName = ((TextView)findViewById(R.id.nameInput)).getText().toString();
        if (monumentName.equals("")) {
            Toast.makeText(this, "ERROR: Debe introducir antes un nombre", Toast.LENGTH_LONG).show();
            return;
        }

        File movieDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        if (!movieDir.exists())
            movieDir.mkdirs();

        video = File.createTempFile(
                monumentName,
                ".3gp",
                movieDir
        );

        Uri videoURI = FileProvider.getUriForFile(this,
                "com.example.android.fileprovider",
                video);

        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoURI);
        activityResultLauncherTakeVideo.launch(takeVideoIntent);
    }

    public void loadPicture(View v) throws IOException {
        String monumentName = ((TextView)findViewById(R.id.nameInput)).getText().toString();
        if (monumentName.equals("")) {
            Toast.makeText(this, "ERROR: Debe introducir antes un nombre", Toast.LENGTH_LONG).show();
            return;
        }

        File pictureDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!pictureDir.exists())
            pictureDir.mkdirs();

        image = File.createTempFile(
                monumentName,  /* prefix */
                ".jpg",         /* suffix */
                pictureDir      /* directory */
        );

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncherLoad.launch(Intent.createChooser(intent, "Select Picture"));
    }

    public void loadVideo(View v) throws IOException {
        String monumentName = ((TextView)findViewById(R.id.nameInput)).getText().toString();
        if (monumentName.equals("")) {
            Toast.makeText(this, "ERROR: Debe introducir antes un nombre", Toast.LENGTH_LONG).show();
            return;
        }

        File movieDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
        if (!movieDir.exists())
            movieDir.mkdirs();

        video = File.createTempFile(
                monumentName,
                ".3gp",
                movieDir
        );

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activityResultLauncherLoadVideo.launch(Intent.createChooser(intent, "Select Video"));
    }
}