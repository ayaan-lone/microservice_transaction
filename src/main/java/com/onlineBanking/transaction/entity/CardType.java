package com.onlineBanking.transaction.entity;
//
//public enum CardType {
//	
//	CREDIT_CARD("Credit Card"),
//	DEBIT_CARD("Credit Card"),
//	BUSINESS_DEBIT_CARD("Credit Card"),
//	BUSINESS_CREDIT_CARD("Credit Card");
//
//	private final String value;
//
//	CardType(String value) {
//		this.value = value;
//	}
//	
//	String getValue(){
//		return value;
//	}
//
//}

import com.onlineBanking.transaction.entity.CardType;

public enum CardType {
    DEBIT_CARD("Debit Card"),
    CREDIT_CARD("Credit Card");

    private String displayName;

    CardType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static CardType fromString(String displayName) {
        for (CardType cardType : CardType.values()) {
            if (cardType.getDisplayName().equalsIgnoreCase(displayName)) {
                return cardType;
            }
        }
        throw new IllegalArgumentException("Unknown card type: " + displayName);
    }
}
