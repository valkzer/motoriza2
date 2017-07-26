package com.example.valkzer.motorizados.Models;

public class CreditCard {

    private String  cardNumber          = "";
    private Integer cardExpirationMonth = 0;
    private Integer cardExpirationYear  = 0;
    private String  cardType            = "";
    private Integer id                  = null;

    public CreditCard(Integer id)
    {
        this.id = id;
    }

    public CreditCard(String cardNumber, Integer cardExpirationMonth, Integer cardExpirationYear, String cardType)
    {
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

    public Integer getId()
    {
        return id;
    }
}
