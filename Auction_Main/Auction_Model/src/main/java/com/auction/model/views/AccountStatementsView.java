/**
 * 
 */
package com.auction.model.views;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author aegis-005
 *
 */
public class AccountStatementsView {

	private Date transactionCreation;
	private String transactionDescription;
	private String product;
	private Integer quantity;
	private Integer referenceId;
	private Float price;
	private BigDecimal total;
	private Float commission;
	private Float vat;
	private Float shipping;
	private BigDecimal debit;
	private BigDecimal credit;
	private BigDecimal balance;
	private String comments;
	private BigDecimal advanceBalance ;

	public AccountStatementsView() {
	}

	/**
	 * @param transactionCreation
	 * @param transactionDescription
	 * @param product
	 * @param quantity
	 * @param transactionId
	 * @param price
	 * @param total
	 * @param commission
	 * @param vat
	 * @param shipping
	 * @param debit
	 * @param credit
	 * @param balance
	 */

	public AccountStatementsView

	(Date transactionCreation, String transactionDescription, Integer referenceId,String comments, String product, Integer quantity,
			Float price, BigDecimal total, Float commission, Float vat, Float shipping, BigDecimal debit,
			BigDecimal credit, BigDecimal advanceBalance, BigDecimal balance) {

		this.transactionCreation = transactionCreation;
		this.transactionDescription = transactionDescription;
		this.referenceId = referenceId;
		this.comments =comments;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
		this.total = total;
		this.commission = getCommissionAmountFromPercentage(commission);
		this.vat = getVatAmountFromPercentage(vat);
		this.shipping = shipping;
		this.debit = debit;
		this.credit = credit;
		this.advanceBalance = advanceBalance;
		this.balance = balance;
		
	}

	public AccountStatementsView(Date transactionCreation, String transactionDescription, Integer referenceId,
			String comments,String product, BigDecimal total, Float commission, Float vat, BigDecimal debit, BigDecimal credit,
			BigDecimal advanceBalance,BigDecimal balance) {

		this.transactionCreation = transactionCreation;
		this.transactionDescription = transactionDescription;
		this.referenceId = referenceId;
		this.comments =comments;
		this.product = product;
		this.total = total;
		this.commission = commission;
		this.vat = vat;
		this.debit = debit;
		this.credit = credit;
		this.advanceBalance=advanceBalance;
		this.balance = balance;
		
	}

	public Date getTransactionCreation() {
		return transactionCreation;
	}

	public void setTransactionCreation(Date transactionCreation) {
		this.transactionCreation = transactionCreation;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Float getCommission() {
		return commission;
	}

	public void setCommission(Float commission) {
		this.commission = commission;
	}

	public Float getVat() {
		return vat;
	}

	public void setVat(Float vat) {
		this.vat = vat;
	}

	public Float getShipping() {
		return shipping;
	}

	public void setShipping(Float shipping) {
		this.shipping = shipping;
	}

	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getDebit() {
		return debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}

	public Integer getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Integer referenceId) {
		this.referenceId = referenceId;
	}
	
	private Float getVatAmountFromPercentage(Float vatPercentage) { 
		BigDecimal vatAmount =( this.total.multiply(new BigDecimal(vatPercentage))).divide(new BigDecimal(100));
		vatAmount=vatAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		return vatAmount.floatValue();
	}
	
	private Float getCommissionAmountFromPercentage(Float commissionPercentage) { 
		BigDecimal commissionAmount =(this.total.multiply(new BigDecimal(commissionPercentage))).divide(new BigDecimal(100));
		commissionAmount=commissionAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
		return commissionAmount.floatValue();
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public BigDecimal getAdvanceBalance() {
		return advanceBalance;
	}

	public void setAdvanceBalance(BigDecimal advanceBalance) {
		this.advanceBalance = advanceBalance;
	}
	

}
