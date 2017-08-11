package com.example.valkzer.motorizados.Repositories.FireBase;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import java.util.Observer;
import java.util.ArrayList;
import java.util.Observable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.example.valkzer.motorizados.Models.Model;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
import com.example.valkzer.motorizados.Models.CreditCard;
import com.example.valkzer.motorizados.Repositories.Interfaces.CreditCardRepositoryInterface;

public class CreditCardFireBaseRepository extends BaseFireBaseRepository implements CreditCardRepositoryInterface {

    private final ArrayList<CreditCard> creditCards = new ArrayList<CreditCard>();
    private final Observable            observable  = this;

    public CreditCardFireBaseRepository()
    {
        super("credit_cards");
    }

    @Override
    public Model create(Model model)
    {
        String     newKey     = this.repositoryReference.push().getKey();
        CreditCard creditCard = (CreditCard) model;
        this.repositoryReference.child(newKey).setValue(creditCard);
        creditCard.setId(newKey);
        return model;
    }

    @Override
    public void find(String id, final Observer observer)
    {
        this.repositoryReference.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                CreditCard creditCard = dataSnapshot.getValue(CreditCard.class);
                creditCard.setId(dataSnapshot.getKey());
                observer.update(observable, creditCard);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    @Override
    public void getAll(final Observer observer)
    {
        this.repositoryReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                CreditCard creditCard = dataSnapshot.getValue(CreditCard.class);
                creditCard.setId(dataSnapshot.getKey());
                creditCards.add(creditCard);
                observer.update(observable, creditCards);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                Integer    childToUpdateIndex = getCreditCardPositionInArray(dataSnapshot.getKey());
                CreditCard creditCard         = dataSnapshot.getValue(CreditCard.class);
                creditCard.setId(dataSnapshot.getKey());
                if (childToUpdateIndex != null) {
                    creditCards.remove((int) childToUpdateIndex);
                    creditCards.add((int) childToUpdateIndex, creditCard);
                    observer.update(observable, creditCards);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {
                Integer childToDeleteIndex = getCreditCardPositionInArray(dataSnapshot.getKey());
                if (childToDeleteIndex != null) {
                    creditCards.remove((int) childToDeleteIndex);
                    observer.update(observable, creditCards);
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    @Override
    public Model update(Model model)
    {
        final CreditCard creditCard   = (CreditCard) model;
        final String     creditCardId = creditCard.getId();

        this.repositoryReference.child(creditCardId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                CreditCard creditCardFromDB = dataSnapshot.getValue(CreditCard.class);

                Integer cardExpirationMonth = creditCard.getCardExpirationMonth() == null ? creditCardFromDB.getCardExpirationMonth() : creditCard.getCardExpirationMonth();
                Integer cardExpirationYear  = creditCard.getCardExpirationYear() == null ? creditCardFromDB.getCardExpirationYear() : creditCard.getCardExpirationYear();
                String  cardNumber          = creditCard.getCardNumber() == null ? creditCardFromDB.getCardNumber() : creditCard.getCardNumber();
                String  cardType            = creditCard.getCardType() == null ? creditCardFromDB.getCardType() : creditCard.getCardType();
                String  deliveryId          = creditCard.getDeliveryId() == null ? creditCardFromDB.getDeliveryId() : creditCard.getDeliveryId();
                String  id                  = creditCard.getId() == null ? creditCardFromDB.getId() : creditCard.getId();

                creditCardFromDB.setCardExpirationMonth(cardExpirationMonth);
                creditCardFromDB.setCardExpirationYear(cardExpirationYear);
                creditCardFromDB.setCardNumber(cardNumber);
                creditCardFromDB.setCardType(cardType);
                creditCardFromDB.setDeliveryId(deliveryId);
                creditCardFromDB.setId(id);

                Map<String, Object> updates = new HashMap<String, Object>();
                updates.put("/" + repositoryReference.getKey() + "/" + dataSnapshot.getKey(), creditCardFromDB);
                updates.put("/deliveries/" + creditCardFromDB.getDeliveryId() + "/creditCard", creditCardFromDB);

                repositoryReference.getRoot().updateChildren(updates);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });

        return model;
    }

    @Override
    public boolean delete(Model model)
    {
        return false;
    }

    private Integer getCreditCardPositionInArray(String id)
    {
        Integer positionInArray = null;
        for (int index = 0; index < creditCards.size(); index++) {
            CreditCard creditCard = creditCards.get(index);
            if (Objects.equals(creditCard.getId(), id)) {
                positionInArray = index;
            }
        }

        return positionInArray;
    }
}
