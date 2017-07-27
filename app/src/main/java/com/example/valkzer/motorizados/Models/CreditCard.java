package com.example.valkzer.motorizados.Models;

import com.example.valkzer.motorizados.Repositories.FireBase.CreditCardFireBaseRepository;

public class CreditCard extends Model {

    private String  cardNumber          = "";
    private Integer cardExpirationMonth = 0;
    private Integer cardExpirationYear  = 0;
    private String  cardType            = "";
    private String  id                  = null;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getDeliveryId()
    {
        return deliveryId;
    }

    public CreditCard setDeliveryId(String deliveryId)
    {
        this.deliveryId = deliveryId;
        return this;
    }

    private String deliveryId = null;

    public CreditCard(String id)
    {
        super(new CreditCardFireBaseRepository());
        this.id = id;
    }

    public CreditCard(String cardNumber, Integer cardExpirationMonth, Integer cardExpirationYear, String cardType)
    {
        super(new CreditCardFireBaseRepository());
        this.cardNumber = cardNumber;
        this.cardExpirationMonth = cardExpirationMonth;
        this.cardExpirationYear = cardExpirationYear;
        this.cardType = cardType;
    }

    public String getCardNumber()
    {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }

    public Integer getCardExpirationMonth()
    {
        return cardExpirationMonth;
    }

    public void setCardExpirationMonth(Integer cardExpirationMonth)
    {
        this.cardExpirationMonth = cardExpirationMonth;
    }

    public Integer getCardExpirationYear()
    {
        return cardExpirationYear;
    }

    public void setCardExpirationYear(Integer cardExpirationYear)
    {
        this.cardExpirationYear = cardExpirationYear;
    }

    public String getCardType()
    {
        return cardType;
    }

    public void setCardType(String cardType)
    {
        this.cardType = cardType;
    }

    public String getId()
    {
        return id;
    }
}
