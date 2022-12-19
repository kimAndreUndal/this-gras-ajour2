package com.gras.database;

import io.agroal.api.AgroalDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

public class DatabaseHandler {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseHandler.class);

    @Inject
    AgroalDataSource dataSource;


    public boolean insertIntoGrasLoan(String customerId,
                                      String providerId,
                                      String financialInstitutionId,
                                      String loanType,
                                      String accountId,
                                      String accountName,
                                      Long originalBalance,
                                      Long balance,
                                      String terms,
                                      Long interestBearingBalance,
                                      Long nonInterestBearingBalance,
                                      int effectiveInterestRate,
                                      int nominalInterestRate,
                                      Long installmentCharges,
                                      String installmentChargesPeriod,
                                      int coBorrower,
                                      Long creditLimit,
                                      Timestamp processedTime,
                                      String processedTimeText
    ){
        boolean ok = true;
        String sql ="INSERT INTO gras.loan(\n" +
                "customer_id, provider_id, financial_institution_id, loan_type, received_time, account_id, account_name, original_balance, balance, terms,\n" +
                "\tinterest_bearing_balance, non_interest_bearing_balance, effective_interest_rate, nominal_interest_rate, installment_charges,\n" +
                "\tinstallment_charges_period, co_borrower, credit_limit, processed_time,processed_time_text, delete_mark)\n" +
                "VALUES (?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'false')\n" +
                "ON CONFLICT ON CONSTRAINT pk_loan\n" +
                "DO UPDATE SET (received_time, account_name, original_balance, balance, terms, interest_bearing_balance, non_interest_bearing_balance,\n" +
                "\t\t\t   effective_interest_rate, nominal_interest_rate, installment_charges, installment_charges_period, co_borrower, credit_limit,\n" +
                "\t\t\t   processed_time, processed_time_text, delete_mark) =(now(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,'false')\n" +
                "WHERE (gras.loan.customer_id =? and gras.loan.provider_id =? and gras.loan.financial_institution_id = ? and gras.loan.loan_type = ?\n" +
                "\tand gras.loan.account_id=? and gras.loan.processed_time <= ?)";
        try(Connection conn = dataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setString(1, customerId);
            pstm.setString(2, providerId);
            pstm.setString(3, financialInstitutionId);
            pstm.setString(4, loanType);
            pstm.setString(6, accountId);
            pstm.setString(7, accountName);
            pstm.setLong(8, originalBalance);
            pstm.setLong(9, balance);
            pstm.setString(10, terms);
            pstm.setLong(11, interestBearingBalance);
            pstm.setLong(12, nonInterestBearingBalance);
            pstm.setLong(13, effectiveInterestRate);
            pstm.setLong(14, nominalInterestRate);
            pstm.setLong(15, installmentCharges);
            pstm.setString(16, installmentChargesPeriod);
            pstm.setInt(17, coBorrower);
            pstm.setLong(18, creditLimit);
            pstm.setTimestamp(19, processedTime);
            pstm.setString(20, processedTimeText);
            pstm.setString(21, accountName);
            pstm.setLong(22, originalBalance);
            pstm.setLong(23, balance);
            pstm.setString(24, terms);
            pstm.setLong(25, interestBearingBalance);
            pstm.setLong(26, nonInterestBearingBalance);
            pstm.setLong(27, effectiveInterestRate);
            pstm.setLong(28, nominalInterestRate);
            pstm.setLong(29, installmentCharges);
            pstm.setString(30, installmentChargesPeriod);
            pstm.setInt(31, coBorrower);
            pstm.setLong(32, creditLimit);
            pstm.setTimestamp(33, processedTime);
            pstm.setString(34, processedTimeText);
            pstm.executeUpdate();

        }catch (SQLException e){
            logger.error("insertIntoGrasLoan: " + e.getMessage());
            ok = false;
        }
        return ok;
    }

    public boolean upsertLoan(
            String customerId,
            String providerId,
            String financialInstitutionId,
            String loanType,
            String accountId,
            String accountName,
            float originalBalance,
            float balance,
            String terms,
            float interestBearingBalance,
            String nonInterestBearingBalance,
            int effectiveInterestRate,
            double nominalInterestRate,
            float installmentCharges,
            String installmentChargesPeriod,
            int coBorrower,
            float creditLimit,
            Timestamp processedTime,
            Timestamp processedTimeText
    ){
        boolean ok = true;
        String sql =
                "INSERT INTO gras.loan(" +
                        "customer_id, provider_id, financial_institution_id, loan_type, received_time, account_id, account_name, original_balance, balance, terms, " +
                        "interest_bearing_balance, non_interest_bearing_balance, effective_interest_rate, nominal_interest_rate, installment_charges, " +
                        "installment_charges_period, co_borrower, credit_limit, processed_time, processed_time_text, delete_mark) " +
                        "VALUES (?, ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'false') " +
                        "ON CONFLICT ON CONSTRAINT pk_loan " +
                        "DO UPDATE SET (received_time, account_name, original_balance, balance, terms, interest_bearing_balance," +
                        " non_interest_bearing_balance, " +
                        "  effective_interest_rate, nominal_interest_rate, installment_charges, installment_charges_period, co_borrower," +
                        " credit_limit, " +
                        "  processed_time, processed_time_text, delete_mark) =(now(), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,'false') " +
                        "WHERE (gras.loan.customer_id =? and gras.loan.provider_id =? and gras.loan.financial_institution_id = ? and gras.loan.loan_type = ? " +
                        "and gras.loan.account_id=? and gras.loan.processed_time <= ?)";
        try(Connection conn = dataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setString(1,customerId);
            pstm.setString(2,providerId);
            pstm.setString(3,financialInstitutionId);
            pstm.setString(4,loanType);
            pstm.setString(5,accountId);
            pstm.setString(6,accountName);
            pstm.setFloat(7,originalBalance);
            pstm.setFloat(8, balance);
            pstm.setString(9, terms);
            pstm.setFloat(10,interestBearingBalance);
            pstm.setString(11, nonInterestBearingBalance);
            pstm.setInt(12, effectiveInterestRate);
            pstm.setDouble(13, nominalInterestRate);
            pstm.setFloat(14,installmentCharges);
            pstm.setString(15, installmentChargesPeriod);
            pstm.setInt(16, coBorrower);
            pstm.setFloat(17, creditLimit);
            pstm.setTimestamp(18, processedTime,  Calendar.getInstance(TimeZone.getTimeZone("GMT")));
            pstm.setTimestamp(19, processedTimeText,  Calendar.getInstance(TimeZone.getTimeZone("GMT")));
            pstm.setString(20, accountName);
            pstm.setFloat(21,originalBalance);
            pstm.setFloat(22, balance);
            pstm.setString(23, terms);
            pstm.setFloat(24,interestBearingBalance);
            pstm.setString(25, nonInterestBearingBalance);
            pstm.setInt(26, effectiveInterestRate);
            pstm.setDouble(27, nominalInterestRate);
            pstm.setFloat(28,installmentCharges);
            pstm.setString(29, installmentChargesPeriod);
            pstm.setInt(30, coBorrower);
            pstm.setFloat(31, creditLimit);
            pstm.setTimestamp(32, processedTime,  Calendar.getInstance(TimeZone.getTimeZone("GMT")));
            pstm.setTimestamp(33, processedTimeText,  Calendar.getInstance(TimeZone.getTimeZone("GMT")));
            pstm.setString(34, customerId);
            pstm.setString(35, providerId);
            pstm.setString(36, financialInstitutionId);
            pstm.setString(37, loanType);
            pstm.setString(38, accountId);
            pstm.setTimestamp(39, processedTime,  Calendar.getInstance(TimeZone.getTimeZone("GMT")));
        }catch (SQLException e){
            logger.error("upsertLoan: " + e.getMessage());
            ok = false;
        }
        return ok;
    }

    public boolean upsertLoanPush(
            String providerId,
            String financialInstitutionId,
            String accountId,
            String accountName,
            Timestamp processedTime,
            boolean deleted){

        boolean ok = true;
        String sql =
                "INSERT INTO gras.loan_push_logs(" +
                        "provider_id, " +
                        "financial_institution_id, " +
                        "received_time, " +
                        "account_id, " +
                        "account_name, " +
                        "processed_time, deleted) " +
                        "VALUES (?, ?, NOW(), ?, ?, ?, ?)";
        try(Connection conn = dataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setString(1, providerId);
            pstm.setString(2, financialInstitutionId);
            pstm.setString(3, accountId);
            pstm.setString(4, accountName);
            pstm.setTimestamp(5, processedTime);
            pstm.setBoolean(6, deleted);
            pstm.executeUpdate();
        }catch (SQLException e){
            logger.error("upsertLoanPush: " + e.getMessage());
            ok = false;
        }
        return ok;
    }

    public int deleteLoan(String customerId,
                          String providerId,
                          String financialInstitutionId,
                          String loanType,
                          String accountId,
                          Timestamp processedTime) {
        int ok = 1;
        String sql =
                "DELETE FROM gras.loan " +
                        "WHERE (gras.loan.customer_id =? " +
                        "and gras.loan.provider_id =? " +
                        "and gras.loan.financial_institution_id = ? " +
                        "and gras.loan.loan_type = ?" +
                        "and gras.loan.account_id=? " +
                        "and gras.loan.processed_time <= ?)";
        try(Connection connection = dataSource.getConnection(); PreparedStatement pstm = connection.prepareStatement(sql)){
            pstm.setString(1, customerId);
            pstm.setString(2, providerId);
            pstm.setString(3, financialInstitutionId);
            pstm.setString(4, loanType);
            pstm.setString(5, accountId);
            pstm.setTimestamp(6, processedTime, Calendar.getInstance(TimeZone.getTimeZone("GMT")));
            pstm.executeUpdate();
        }catch (SQLException e){
            logger.error("deleteLoan: " + e.getMessage());
            ok = 0;
        }
        return ok;
    }

    public int deleteCustomersForFi(String customerId, String providerId, String financialInstitutionId){
        int ok = 1;
        String sql =
                "DELETE FROM gras.loan " +
                        "WHERE customer_id= ? " +
                        "and provider_id = ? " +
                        "and financial_institution_id = ?";
        try(Connection conn = dataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setString(1, customerId);
            pstm.setString(2, providerId);
            pstm.setString(3, financialInstitutionId);
            pstm.executeUpdate();
        }catch (SQLException e){
            logger.error("deleteCustomersForFi: " + e.getMessage());
            ok = 0;
        }
        return  ok;
    }

    public boolean insertOppdateringsLogg(String providerId, String finanalInstitution){
        boolean ok = true;
        String sql =
                "insert into gras.oppdaterings_logg " +
                        "(provider_id,financial_institution_id,start_time)" +
                        "VALUES" +
                        "(?,?,now()::date)" +
                        "ON CONFLICT ON CONSTRAINT pk_oppdaterings_logg " +
                        "DO NOTHING";
        try(Connection conn = dataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setString(1, providerId);
            pstm.setString(2, finanalInstitution);
            pstm.executeUpdate();
        } catch (SQLException e) {
            logger.error("insertOppdateringsLogg: " + e.getMessage());
            ok = false;
        }
        return ok;
    }

    public boolean updateOppdateringsLogg(String providerId){
        boolean ok = true;
        String sql =
                "UPDATE gras.oppdaterings_logg " +
                        "SET stop_time = now():: date " +
                        "WHERE start_time = now():: date " +
                        "AND provider_id = ?";
        try(Connection conn = dataSource.getConnection(); PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, providerId);
            pstm.executeUpdate();
        } catch (SQLException e) {
            logger.error("updateOppdateringsLogg: " + e.getMessage());
            ok = false;
        }
        return ok;
    }
}
