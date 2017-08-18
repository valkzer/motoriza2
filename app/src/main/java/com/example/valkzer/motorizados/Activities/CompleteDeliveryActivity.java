package com.example.valkzer.motorizados.Activities;

import android.net.Uri;
import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Environment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.EditText;
import android.content.Context;
import android.widget.ImageView;
import android.provider.MediaStore;
import android.graphics.BitmapFactory;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.valkzer.motorizados.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.valkzer.motorizados.Models.Delivery;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Observable;

public class CompleteDeliveryActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 1;
    private String           deliveryId;
    private ImageView        identificationPicture;
    private StorageReference mStorageRef;
    private Delivery         delivery;
    private String identificationPictureUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_delivery);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        fillCreditCardTypeSpinner();

        Intent intent = getIntent();
        this.deliveryId = intent.getStringExtra("deliveryId");

        this.identificationPicture = (ImageView) findViewById(R.id.imgIdentification);
        checkPermissions();
    }

    private void fillCreditCardTypeSpinner()
    {
        ArrayList<String> options = new ArrayList<String>();

        options.add("VISA");
        options.add("MASTER CARD");
        options.add("AMERICAN EXPRESS");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
        ((Spinner) findViewById(R.id.cboCreditCardType)).setAdapter(adapter);
    }

    public void completeDelivery(View view)
    {
        Delivery delivery = new Delivery();
        final Observer observer = new Observer() {
            @Override
            public void update(Observable o, Object arg)
            {
                Delivery delivery = (Delivery) arg;

                String customerIdentification = ((EditText) findViewById(R.id.txtCustomerIdentification)).getText().toString();

                Double  customerSalary;
                Integer creditCardMonth;
                Integer creditCardYear;
                Double  deliveryCost;
                String  creditCardType;


                try {
                    customerSalary = Double.parseDouble(((EditText) findViewById(R.id.txtCustomerSalary)).getText().toString());
                } catch (Exception e) {
                    customerSalary = 0.0;
                }
                String customerWorkPlace = ((EditText) findViewById(R.id.txtWorkplace)).getText().toString();
                String creditCardNumber  = ((EditText) findViewById(R.id.txtCreditCardNumber)).getText().toString();

                try {
                    creditCardMonth = Integer.parseInt(((EditText) findViewById(R.id.txtCreditCardExpMonth)).getText().toString());
                } catch (Exception e) {
                    creditCardMonth = 0;
                }
                try {
                    creditCardYear = Integer.parseInt(((EditText) findViewById(R.id.txtCreditCardExpYear)).getText().toString());
                } catch (Exception e) {
                    creditCardYear = 0;
                }

                try {
                    deliveryCost = Double.parseDouble(((EditText) findViewById(R.id.txtCustomerSalary)).getText().toString());
                } catch (Exception e) {
                    deliveryCost = 0.0;
                }

                creditCardType = ((Spinner) findViewById(R.id.cboCreditCardType)).getSelectedItem().toString();

                delivery.getCustomer().setIdentification(customerIdentification);
                delivery.getCustomer().setSalary(customerSalary);
                delivery.getCustomer().setWorkPlace(customerWorkPlace);
                delivery.getCustomer().setIdentificationPicture(identificationPictureUrl);

                delivery.getCreditCard().setCardNumber(creditCardNumber);
                delivery.getCreditCard().setCardExpirationYear(creditCardYear);
                delivery.getCreditCard().setCardExpirationMonth(creditCardMonth);
                delivery.getCreditCard().setCardType(creditCardType);

                delivery.setCost(deliveryCost);
                delivery.setCompleted(true);

                delivery.update();
                //TODO: transalation of this, image uploading
                Toast.makeText(CompleteDeliveryActivity.this, "Delivery Completed", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), DeliveryOverviewActivity.class);
                intent.putExtra("deliveryId", delivery.getId());
                startActivity(intent);
                finish();
            }
        };
        delivery.find(this.deliveryId, observer, false);
    }

    public void selectIdentificationPicture(View view)
    {
        File   file              = new File(Environment.getExternalStorageDirectory(), "id_" + MD5(this.deliveryId) + ".jpg");
        Uri    uri               = Uri.fromFile(file);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 99);
        }
    }

    private void checkPermissions()
    {
        int readPermission  = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int writePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (readPermission != PackageManager.PERMISSION_GRANTED || writePermission != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_STORAGE);
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent resultData)
    {

        final Context context = this;
        if (requestCode == 99 && resultCode == RESULT_OK) {
            Uri         uri      = resultData.getData();
            Bitmap      bitmap   = null;
            InputStream is       = null;
            String      fileName = uri.getLastPathSegment();

            StorageReference storageReference = mStorageRef.child("pictures/" + fileName);

            storageReference.putFile(uri)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                                    (new Delivery()).find(deliveryId, new Observer() {
                                        @Override
                                        public void update(Observable o, Object arg)
                                        {
                                            Delivery delivery = (Delivery) arg;
                                            delivery.getCustomer().setIdentificationPicture(downloadUrl.toString());
                                            delivery.update();
                                        }
                                    }, false);


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception)
                                {
                                    Toast.makeText(context, "ERROR!", Toast.LENGTH_SHORT).show();
                                }
                            });


            try {
                is = this.getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(is);
                if (is != null) {
                    is.close();
                }
                identificationPicture.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String MD5(String md5)
    {
        try {
            java.security.MessageDigest md            = java.security.MessageDigest.getInstance("MD5");
            byte[]                      digest        = md.digest(md5.getBytes());
            StringBuilder               stringBuilder = new StringBuilder();
            for (byte aDigest : digest) {
                stringBuilder.append(Integer.toHexString((aDigest & 0xFF) | 0x100).substring(1, 3));
            }
            return stringBuilder.toString();
        } catch (java.security.NoSuchAlgorithmException ignored) {
        }
        return null;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {

    }
}
