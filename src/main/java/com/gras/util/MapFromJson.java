package com.gras.util;

import com.gras.dto.CustomerDto;
import com.gras.dto.DocumentDto;
import com.gras.dto.LoanDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.print.Doc;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@ApplicationScoped
public class MapFromJson {
    private static final Logger logger = LoggerFactory.getLogger(MapFromJson.class);
    public String getProviderID(DocumentDto documentDto){
        return documentDto.providerID;
    }
    public String getpostType(DocumentDto documentDto){
        return documentDto.posttype;
    }

    public Map<String, String>getCustomerDto(DocumentDto documentDto){
        Map<String, String> map = new HashMap<>();
        for (CustomerDto customer: documentDto.customers
             ) {
            map.put("customerID", customer.customerID);
            map.put("financialInstitutionID", customer.financialInstitutionID);
        }
        return map;
    }


    public LoanDto getLoan(DocumentDto documentDto){
        LoanDto loanDto = new LoanDto();
        for (CustomerDto customer: documentDto.customers
             ) {
            loanDto = publicLoanFields.apply(customer);
        }
        return loanDto;
    }

    private final Function<CustomerDto, LoanDto> publicLoanFields = loans ->{
       LoanDto loanDto = new LoanDto();
        for (LoanDto loan: loans.getLoan()
             ) {
            loanDto.balance = mapToZeroIfNegative(loan.balance);
            loanDto.terms = loan.terms;
            loanDto.originalBalance = mapToZeroIfNegative(loan.originalBalance);
            loanDto.interestBearingBalance = mapToZeroIfNegative(loan.interestBearingBalance);
            loanDto.nonInterestBearingBalance = mapToZeroIfNegative(loan.nonInterestBearingBalance);
            loanDto.effectiveInterestRate = mapToZeroIfNegative(loan.effectiveInterestRate);
            loanDto.coBorrower = loan.coBorrower;
            loanDto.nominalInterestRate = loan.nominalInterestRate;
            loanDto.installmentCharges = loan.installmentCharges;
            loanDto.installmentChargePeriod = loan.installmentChargePeriod;
            loanDto.creditLimit = loan.creditLimit;
            loanDto.accountID = loan.accountID;
            loanDto.loanType = loan.loanType;
            loanDto.accountName = loan.accountName;
            loanDto.processedTime = loan.processedTime;
            loanDto.receivedTime = String.valueOf(new java.sql.Timestamp(System.nanoTime()));
        }
        return loanDto;
    };
    String mapToZeroIfNegative(String value) {
        if (value == null) {
            return value;
        }

        try {
            if (Long.parseLong(value) < 0) {
                return "0";
            }

            return String.valueOf(Long.parseLong(value));
        }
        catch (NumberFormatException e) {
            logger.error("Value should be numeric", e);
            return "0";
        }
    }
}
