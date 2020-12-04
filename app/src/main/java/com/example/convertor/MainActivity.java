package com.example.convertor;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;


public class MainActivity extends AppCompatActivity
{
    Button choose_btn;
    ImageView photo;
    Bitmap imageBitmap;
    String textObtained;

    Uri mImageUri;

    public void crop(View view)//choose pic
    {
        Log.i("Button pressed","true");
        CropImage.activity().start(MainActivity.this);
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            //choose_btn.setVisibility(View.INVISIBLE);
            //photo.setImageBitmap(imageBitmap);
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            mImageUri=result.getUri();
            //choose_btn.setVisibility(View.INVISIBLE);

            photo.setImageURI(mImageUri);
            BitmapDrawable bitmapDrawable = (BitmapDrawable)photo.getDrawable();
            Bitmap bitmap = bitmapDrawable.getBitmap();


            TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();

            if(!recognizer.isOperational()){
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
            else{
                //TextRecognizer - Finds and recognizes text in a supplied Frame.
                //Object created for the Frame which will bounce through each word, considering each word as a separate frame and each space between the words are for breaking one frame from another

                Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                SparseArray<TextBlock> items = recognizer.detect(frame); //Finds and recognizes text in a supplied Frame.

                    /*The principal operations on a StringBuilder are the append and insert methods, which are overloaded so as to accept data of any type.
                     Each effectively converts a given datum to a string and then appends or inserts the characters of that string to the string builder.
                      The append method always adds these characters at the end of the builder; the insert method adds the characters at a specified point.*/

                StringBuilder sb = new StringBuilder();
                //get text from sb until there is no text
                for(int i = 0; i<items.size(); i++){
                    TextBlock myItem = items.valueAt(i);
                    sb.append(myItem.getValue());
                    sb.append("\n");
                }
                //set text to edit text
                //ToString() Returns a string representation of the object.
                //TODO mResultEt.setText(sb.toString());
                textObtained=sb.toString();
                //Toast.makeText(this, "ans="+sb.toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), resultActivity.class);
                intent.putExtra("textResult", textObtained);
                startActivity(intent);
            }
        }
        else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
            //if there is any error show it

            Toast.makeText(this, "There was an error please try again", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        photo=(ImageView) findViewById(R.id.imageView);
        choose_btn=(Button) findViewById(R.id.takepic_btn);
    }
}