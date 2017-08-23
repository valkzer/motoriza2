package com.example.valkzer.motorizados.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valkzer.motorizados.Models.Delivery;
import com.example.valkzer.motorizados.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;

public class DeliveryOverviewActivity extends AppCompatActivity {

    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_overview);


        mStorageRef = FirebaseStorage.getInstance().getReference();

        Intent intent     = getIntent();
        String deliveryId = intent.getStringExtra("deliveryId");

        loadDeliveryInformation(deliveryId);
    }

    private void loadDeliveryInformation(String deliveryId)
    {
        Delivery delivery = new Delivery();
        Observer observer = new Observer() {
            @Override
            public void update(Observable o, Object arg)
            {
                Delivery delivery = (Delivery) arg;

                TextView lblCustomerName           = ((TextView) findViewById(R.id.lblCustomerName));
                TextView lblCustomerAddress        = ((TextView) findViewById(R.id.lblCustomerAddress));
                TextView lblCustomerPhone          = ((TextView) findViewById(R.id.lblCustomerPhone));
                TextView lblCustomerIdentification = ((TextView) findViewById(R.id.lblCustomerIdentification));
                TextView lblCustomerSalary         = ((TextView) findViewById(R.id.lblCustomerSalary));

                TextView lblCreditCardNumber          = ((TextView) findViewById(R.id.lblCreditCardNumber));
                TextView lblCreditCardType            = ((TextView) findViewById(R.id.lblCreditCardType));
                TextView lblCreditCardExpirationMonth = ((TextView) findViewById(R.id.lblCreditCardExpirationMonth));
                TextView lblCreditCardExpirationYear  = ((TextView) findViewById(R.id.lblCreditCardExpirationYear));

                TextView lblDeliveryCost        = ((TextView) findViewById(R.id.lblDeliveryCost));
                TextView lblDeliveryDescription = ((TextView) findViewById(R.id.lblDeliveryDescription));

                char[] maskedCreditCardNumberCharacters = delivery.getCreditCard().getCardNumber().toCharArray();
                int    charCountToMask                  = maskedCreditCardNumberCharacters.length - 4;

                for (int counter = 0; counter < charCountToMask; counter++) {
                    maskedCreditCardNumberCharacters[counter] = '*';
                }

                String maskedCreditCard = String.valueOf(maskedCreditCardNumberCharacters);

                lblCustomerName.setText(delivery.getCustomer().getName());
                lblCustomerAddress.setText(delivery.getCustomer().getAddress());
                lblCustomerPhone.setText(delivery.getCustomer().getPhone());
                lblCustomerIdentification.setText(delivery.getCustomer().getIdentification());
                lblCustomerSalary.setText(delivery.getCustomer().getSalary().toString());

                lblCreditCardNumber.setText(maskedCreditCard);
                lblCreditCardType.setText(delivery.getCreditCard().getCardType());
                lblCreditCardExpirationMonth.setText(delivery.getCreditCard().getCardExpirationMonth().toString());
                lblCreditCardExpirationYear.setText(delivery.getCreditCard().getCardExpirationYear().toString());

                lblDeliveryCost.setText(delivery.getCost().toString());
                lblDeliveryDescription.setText(delivery.getDescription());

                ImageView mainImage = (ImageView) findViewById(R.id.imgCustomerIdentificationPicture);
                try {
                    Picasso.with(getApplicationContext()).load(delivery.getCustomer().getIdentificationPicture()).into(mainImage);
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                    Toast.makeText(getApplicationContext(), getString(R.string.failed_to_load_image), Toast.LENGTH_SHORT).show();
                }
            }
        };

        delivery.find(deliveryId, observer, true);
    }

    private void downloadCustomerIdentificationPicture(final String pictureUri)
    {
        File localFile = null;
        try {
            localFile = File.createTempFile("images", "jpg");
//            Uri              uri            = Uri.parse(pictureUri);
            StorageReference imageReference = mStorageRef.child(pictureUri);

            final File finalLocalFile = localFile;
            imageReference.getFile(localFile)
                          .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                              @Override
                              public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot)
                              {
                                  ImageView mainImage = (ImageView) findViewById(R.id.imgIdentification);
                                  String    imagePath = finalLocalFile.getAbsolutePath();
                                  mainImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
                              }
                          }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception)
                {

                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
