package com.auction.commons.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;

import com.auction.commons.enums.ENUM_AccountStatusCodes;

public class EmailTemplate {

    //private static final String SUB_VERIFY_EMAIL_ADDRESS = "FarmEdge :: Verify Your Email Address";
    //private static final String SUB_PASSWORD_RESET = "FarmEdge :: Password Reset Information";
    //private static final String SUB_EMAIL_ADDRESS_VERIFICATION = "FarmEdge :: Email Address Verification";
    //private static final String SUB_ACCOUNT_STATUS_CHANGES = "FarmEdge :: Account Status Changed";
    //private static final String SUB_AGENT_ACCOUNT_APPROVE = "FarmEdge :: Account Pending Owner Approval";
    //private static final String SUB_OWNER_APPROVAL = "FarmEdge :: Account requires owner approval";
    private static final String LINK_BTN_STYLE = "style='border-radius: 3px;background-color:#ff8400!important; padding: 8px 12px; color: #fff;text-decoration: none;font-size: 18px;font-weight: bolder;'";

    public static String getHostContext(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    public static EmailData verifyEmailAddress(String userName, String toAddress, String iNetPath, MessageSource messageSource, Locale locale) {
        StringBuilder emailBody = new StringBuilder();
        if (locale.getDisplayLanguage() == "English") {
            emailBody.append("<div style='border: 5px solid #ddd;color: #555;font-family: Arial;direction:ltr;font-size:15px'>");
        } else {
            emailBody.append("<div style='border: 5px solid #ddd;color: #555;font-family: Arial;direction:rtl;font-size:20px'>");
        }
        //        emailBody.append("<div style='border: 5px solid #ddd;color: #555;font-family: Arial;font-size:15px'>");
        emailBody.append(getHeader(messageSource.getMessage("emailtemplate.subheader.menu.congratulations", null, locale), true));
        emailBody.append("<div style='min-height:10px;padding:5px 8px;'>");
        emailBody.append("<div><p style='padding:15px 0 0;margin:0px 0 20px; color: #b36f6f; border-top: 2px solid #ff8400;'>");
        emailBody.append("<strong>" + messageSource.getMessage("emailtemplate.common.lbl.hello", null, locale) + " " + userName + "</strong>");
        emailBody.append("</p></div><div>");
        emailBody.append("<p style='padding:0;margin:0;'>");
        emailBody.append(messageSource.getMessage("emailtemplate.verifyemailaddress.lbl.verifyemail1", null, locale) + "<span style='color:#26883f;font-weight: bolder;'>" + " " + messageSource.getMessage("emailtemplate.common.lbl.successfully", null, locale));
        emailBody.append("</span></p>");
        emailBody.append("<p style='padding:0;margin: 20px 0 0;'>" + messageSource.getMessage("emailtemplate.verifyemailaddress.lbl.verifyemail2", null, locale) + "<br><br><br>");
        emailBody.append("<a href='" + iNetPath + "' target='_blank' " + LINK_BTN_STYLE + ">" + messageSource.getMessage("emailtemplate.verifyemailaddress.lbl.verifyemail", null, locale) + "</a></p>");
        emailBody.append("</div>");
        emailBody.append(getRegards(messageSource, locale));
        emailBody.append("</div>");
        emailBody.append(getFooter(messageSource, locale));
        emailBody.append("</div>");

        EmailData edata = new EmailData();
        edata.setHtmlContent(emailBody.toString());
        edata.setSubject(messageSource.getMessage("emailtemplate.header.menu.sub.verify.email.address", null, locale));
        edata.setToAddress(toAddress);
        return edata;
    }

    public static EmailData emailAddressVerified(String userName, String toAddress, boolean varified, boolean isAgent, MessageSource messageSource, Locale locale) {
        StringBuilder emailBody = new StringBuilder();
        if (locale.getDisplayLanguage() == "English") {
            emailBody.append("<div style='border: 5px solid #ddd;color: #555;font-family: Arial;direction:ltr;font-size:15px'>");
        } else {
            emailBody.append("<div style='border: 5px solid #ddd;color: #555;font-family: Arial;direction:rtl;font-size:20px'>");
        }
        emailBody.append(getHeader((varified ? messageSource.getMessage("emailtemplate.subheader.menu.congratulationsfailed", null, locale) : messageSource.getMessage("emailtemplate.subheader.menu.failed", null, locale)), varified));
        emailBody.append("<div style='min-height:10px;padding:5px 8px;'>");
        emailBody.append("<div><p style='padding:15px 0 0; margin:0px 0 20px; color: #b36f6f; border-top: 2px solid #ff8400;'>");
        emailBody.append("<strong>" + messageSource.getMessage("emailtemplate.common.lbl.hello", null, locale) + " " + userName + "</strong>");
        emailBody.append("</p></div><div>");
        if (varified) {
            emailBody.append("<p style='padding:0;margin:0;'>");
            emailBody.append(messageSource.getMessage("emailtemplate.emailaddressverified.lbl.yourverifyemail", null, locale) + "<span style='color:#26883f;font-weight: bolder;'>" + " " + messageSource.getMessage("emailtemplate.common.lbl.successfully", null, locale));
            emailBody.append("</span></p>");
            emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.emailaddressverified.lbl.notabletoaccess", null, locale) + "</p>");
        } else {
            emailBody.append("<p style='padding:0;margin:0;'>");
            emailBody.append(messageSource.getMessage("emailtemplate.emailaddressverified.lbl.sorry", null, locale) + "<span style='color:#f00;font-weight: bolder;'>" + " " + messageSource.getMessage("emailtemplate.common.lbl.notverified", null, locale));
            emailBody.append("</span></p>");
            emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.emailaddressverified.lbl.notabletoaccess", null, locale) + "</p>");
        }
        emailBody.append("</div>");
        emailBody.append(getRegards(messageSource, locale));
        emailBody.append("</div>");
        emailBody.append(getFooter(messageSource, locale));
        emailBody.append("</div>");

        EmailData edata = new EmailData();
        edata.setHtmlContent(emailBody.toString());
        edata.setSubject(messageSource.getMessage("emailtemplate.header.menu.sub.email.address.verification", null, locale));
        edata.setToAddress(toAddress);
        return edata;
    }

    public static EmailData forgotPassword(String userName, String toAddress, String iNetPath, MessageSource messageSource, Locale locale) {

        StringBuilder emailBody = new StringBuilder();
        if (locale.getDisplayLanguage() == "English") {
            emailBody.append("<div style='border: 5px solid #ddd;color: #555;font-family: Arial;direction:ltr;font-size:15px'>");
        } else {
            emailBody.append("<div style='border: 5px solid #ddd;color: #555;font-family: Arial;direction:rtl;font-size:20px'>");
        }
        emailBody.append(getHeader(messageSource.getMessage("emailtemplate.subheader.menu.passwordreset", null, locale), true));
        emailBody.append("<div style='min-height:10px;padding:5px 8px;'>");
        emailBody.append("<div><p style='padding:15px 0 0;margin:0px 0 20px; color: #b36f6f; border-top: 2px solid #ff8400;'>");
        emailBody.append("<strong>" + messageSource.getMessage("emailtemplate.common.lbl.hello", null, locale) + " " + userName + "</strong>");
        emailBody.append("</p></div><div>");
        emailBody.append("<p style='padding:0;margin: 20px 0 0;'>" + messageSource.getMessage("emailtemplate.forgotpassword.lbl.requestedapassword", null, locale) + "<br><br>");
        emailBody.append("<a href='" + iNetPath + "' target='_blank' " + LINK_BTN_STYLE + ">" + " " + messageSource.getMessage("emailtemplate.forgotpassword.lbl.resetPassword", null, locale) + "</a></p>");
        emailBody.append("<p style='padding:0;margin: 20px 0 0;'>");
        emailBody.append(messageSource.getMessage("emailtemplate.forgotpassword.lbl.notrequest", null, locale));
        emailBody.append("</p>");
        emailBody.append("</div>");
        emailBody.append(getRegards(messageSource, locale));
        emailBody.append("</div>");
        emailBody.append(getFooter(messageSource, locale));
        emailBody.append("</div>");

        EmailData edata = new EmailData();
        edata.setHtmlContent(emailBody.toString());
        edata.setSubject(messageSource.getMessage("emailtemplate.header.menu.sub.password.reset", null, locale));
        edata.setToAddress(toAddress);
        return edata;

    }

    public static EmailData pendingForApprovalOfAccount(String userName, String toAddress, boolean varified, MessageSource messageSource, Locale locale) {

        StringBuilder emailBody = new StringBuilder();
        if (locale.getDisplayLanguage() == "English") {
            emailBody.append("<div style='border: 5px solid #ddd;color: #555;font-family: Arial;direction:ltr;font-size:15px'>");
        } else {
            emailBody.append("<div style='border: 5px solid #ddd;color: #555;font-family: Arial;direction:rtl;font-size:20px'>");
        }
        emailBody.append(getHeader(messageSource.getMessage("emailtemplate.subheader.menu.accountpending", null, locale), varified));
        emailBody.append("<div style='min-height:10px;padding:5px 8px;'>");
        emailBody.append("<div><p style='padding:15px 0 0;margin:0px 0 20px; color: #b36f6f; border-top: 2px solid #ff8400;'>");
        emailBody.append("<strong>" + messageSource.getMessage("emailtemplate.common.lbl.hello", null, locale) + " " + userName + "</strong>");
        emailBody.append("</p></div><div>");

        if (varified) {
            emailBody.append("<p style='padding:0;margin:0;'>");
            emailBody.append(messageSource.getMessage("emailtemplate.pendingforapprovalofaccount.lbl.inform2", null, locale) + "<span style='color:#26883f;font-weight: bolder;'>" + "<b>" + messageSource.getMessage("emailtemplate.common.successfully", null, locale) + " </span>");
            emailBody.append(messageSource.getMessage("emailtemplate.pendingforapprovalofaccount.lbl.profileinfo", null, locale) + "</p>");
            emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.pendingforapprovalofaccount.lbl.accountowne", null, locale) + "</p>");
            emailBody.append("<p style='padding:0;margin:0;'></p>");
        } else {
            emailBody.append("<p style='padding:0;margin:0;'>");
            emailBody.append(messageSource.getMessage("emailtemplate.pendingforapprovalofaccount.lbl.sorryFarmEdgeaccount", null, locale) + "<span style='color:#F00;font-weight:bolder;'>" + " " + messageSource.getMessage("auctionrequestapprovallist.auctionstatus.rejected", null, locale) + "</span> by your owner.</p>");
            emailBody.append("</p>");
            emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.pendingforapprovalofaccount.lbl.pleasecontacttoyourowner", null, locale) + "</p>");
        }

        emailBody.append("</div>");

        emailBody.append(getRegards(messageSource, locale));
        emailBody.append("</div>");
        emailBody.append(getFooter(messageSource, locale));
        emailBody.append("</div>");

        EmailData edata = new EmailData();
        edata.setHtmlContent(emailBody.toString());
        edata.setSubject(messageSource.getMessage("emailtemplate.header.menu.sub.agent.account.approve", null, locale));
        edata.setToAddress(toAddress);
        return edata;
    }

    public static EmailData ownerApproval(String ownerUserName, String toAddress, String agentName, String agentEmailAddress, MessageSource messageSource, Locale locale) {

        StringBuilder emailBody = new StringBuilder();
        if (locale.getDisplayLanguage() == "English") {
            emailBody.append("<div style='border: 5px solid #ddd;color: #555;font-family: Arial;direction:ltr;font-size:15px'>");
        } else {
            emailBody.append("<div style='border: 5px solid #ddd;color: #555;font-family: Arial;direction:rtl;font-size:20px'>");
        }
        emailBody.append(getHeader(messageSource.getMessage("emailtemplate.subheader.menu.required", null, locale), true));
        emailBody.append("<div style='min-height:10px;padding:5px 8px;'>");
        emailBody.append("<div><p style='padding:15px 0 0;margin:0px 0 20px; color: #b36f6f; border-top: 2px solid #ff8400;'>");
        emailBody.append("<strong>" + messageSource.getMessage("emailtemplate.common.lbl.hello", null, locale) + " " + ownerUserName + "</strong>");
        emailBody.append("</p></div><div>");
        emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.pendingforapprovalofaccount.lbl.inform", null, locale) + "<b>" + agentName + "</b> " + agentEmailAddress
                + "  <span style='color:#26883f;font-weight:bolder;'>" + " " + messageSource.getMessage("emailtemplate.common.successfully", null, locale) + "</span> " + messageSource.getMessage("emailtemplate.ownerapproval.lbl.registeredwithus", null, locale) + "</p>");
        emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.ownerapproval.lbl.approvesuspend", null, locale) + "</p>");
        emailBody.append("</div>");
        emailBody.append(getRegards(messageSource, locale));
        emailBody.append("</div>");
        emailBody.append(getFooter(messageSource, locale));
        emailBody.append("</div>");

        EmailData edata = new EmailData();
        edata.setHtmlContent(emailBody.toString());
        edata.setSubject(messageSource.getMessage("emailtemplate.header.menu.sub.owner.approval", null, locale));
        edata.setToAddress(toAddress);
        return edata;
    }

    public static EmailData accountActivation(String username, String toAddress, short statusCode, MessageSource messageSource, Locale locale) {
        boolean varified = true;
        if (statusCode == ENUM_AccountStatusCodes.DELETED.getStatus() || statusCode == ENUM_AccountStatusCodes.BLOCKED.getStatus()
                || statusCode == ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus() || statusCode == ENUM_AccountStatusCodes.SUSPENDED_BY_ADMIN.getStatus()) {
            varified = false;
        }

        StringBuilder emailBody = new StringBuilder();
        if (locale.getDisplayLanguage() == "English") {
            emailBody.append("<div style='border: 5px solid #ddd;color: #555;font-family: Arial;direction:ltr;font-size:15px'>");
        } else {
            emailBody.append("<div style='border: 5px solid #ddd;color: #555;font-family: Arial;direction:rtl;font-size:20px'>");
        }
        emailBody.append(getHeader(messageSource.getMessage("emailtemplate.subheader.menu.accountstatus", null, locale), varified));
        emailBody.append("<div style='min-height:10px;padding:5px 8px;'>");
        emailBody.append("<div><p style='padding:15px 0 0;margin:0px 0 20px; color: #b36f6f; border-top: 2px solid #ff8400;'>");
        emailBody.append("<strong>" + messageSource.getMessage("emailtemplate.common.lbl.hello", null, locale) + " " + username + "</strong>");
        emailBody.append("</p></div><div>");

        if (statusCode == ENUM_AccountStatusCodes.DELETED.getStatus() || statusCode == ENUM_AccountStatusCodes.BLOCKED.getStatus()) {
            emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.knowaccount", null, locale) + " <span style='color:#F00;font-weight:bolder;'>"
                    + ENUM_AccountStatusCodes.getDesc(statusCode).toLowerCase() + "</span>.</p>");
        } else if (statusCode == ENUM_AccountStatusCodes.SUSPENDED_BY_ADMIN.getStatus()) {
            emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl", null, locale) + " <span style='color:#F00;font-weight:bolder;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.suspendedmanagement", null, locale) + "</span>.</p>");
            emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.contactmanagement", null, locale) + "</p>");
        } else if (statusCode == ENUM_AccountStatusCodes.SUSPENDED_BY_OWNER.getStatus()) {
            emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.sorryyouraccount", null, locale) + " <span style='color:#F00;font-weight:bolder;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.suspendedowner", null, locale) + "</span>.</p>");
            emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.contactaccountowner", null, locale) + "</p>");
        } else if (statusCode == ENUM_AccountStatusCodes.PENDING_ADMIN_APPROVAL.getStatus()) {
            emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.accountpendingmanagementapproval", null, locale) + " " + "</p>");
            emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.managementapproves", null, locale) + "</p>");
        } else if (statusCode == ENUM_AccountStatusCodes.PENDING_OWNER_APPROVAL.getStatus()) {
            emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.accountpendingowner", null, locale) + "</p>");
            emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.ownerapproves", null, locale) + "</p>");
        } else if (statusCode == ENUM_AccountStatusCodes.ACTIVE.getStatus()) {
            if (locale.getDisplayLanguage() == "English") {
                emailBody.append(
                        "<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.youraccount", null, locale) + "<span style='color:#26883f;font-weight:bolder;'>"
                        + ENUM_AccountStatusCodes.getDesc(statusCode) + "</span>.</p>");
                emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.registeredrole", null, locale));
            } else {
                emailBody.append(
                        "<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.youraccount", null, locale) + "<span style='color:#26883f;font-weight:bolder;'>"
                        + messageSource.getMessage("emailtemplate.accountactivation.lbl.accountstatus", null, locale) + "</span>.</p>");
                emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.registeredrole", null, locale));
            }
        } else {
            if (varified) {
                emailBody.append(
                        "<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.youraccount", null, locale) + " <span style='color:#26883f;font-weight:bolder;'>"
                        + ENUM_AccountStatusCodes.getDesc(statusCode) + "</span>.</p>");
                emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.registeredrole", null, locale));
            } else {
                emailBody.append(
                        "<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.accountstatuschanged", null, locale) + " <span style='color:#F00;font-weight:bolder;'>"
                        + ENUM_AccountStatusCodes.getDesc(statusCode) + "</span>.</p>");
                emailBody.append("<p style='padding:0;margin:0;'>" + messageSource.getMessage("emailtemplate.accountactivation.lbl.contactmanagement", null, locale) + "</p>");
            }
        }

        emailBody.append("</div>");
        emailBody.append(getRegards(messageSource, locale));
        emailBody.append("</div>");
        emailBody.append(getFooter(messageSource, locale));
        emailBody.append("</div>");

        EmailData edata = new EmailData();
        edata.setHtmlContent(emailBody.toString());
        edata.setSubject(messageSource.getMessage("emailtemplate.header.menu.sub.account.status.changes", null, locale));
        edata.setToAddress(toAddress);
        return edata;
    }

    private static String getHeader(String titleText, boolean varified) {
        StringBuilder emailBody = new StringBuilder();
        if (varified) {
            emailBody.append("<div style='text-align: center; margin: 0 5px; color: #26883f; padding: 10px 0 10px;font-weight:bolder;font-size:26px'>");
            emailBody.append(titleText);
            emailBody.append("</div>");
        } else {
            emailBody.append("<div style='text-align: center; margin: 0 5px; color: #f00; padding: 10px 0 10px;font-weight:bolder;font-size:26px'>");
            emailBody.append(titleText);
            emailBody.append("</div>");
        }
        return emailBody.toString();
    }

    private static String getRegards(MessageSource messageSource, Locale locale) {
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("<div style='padding: 0 0 10px; margin: 20px 0 0px; color: #b36f6f; border-bottom: 2px solid #ff8400;'>");
        emailBody.append("<strong>" + messageSource.getMessage("emailtemplate.common.lbl.regards", null, locale) + "</strong>");
        emailBody.append("<p style='padding:0;margin:3px 0 0;'>" + messageSource.getMessage("emailtemplate.common.lbl.team", null, locale) + "</p></div>");
        return emailBody.toString();
    }

    private static String getFooter(MessageSource messageSource, Locale locale) {
        StringBuilder emailBody = new StringBuilder();
        emailBody.append("<div style='text-align: center; color: #fff; margin: 0; background: #41a15a; padding: 15px 0; border-top: 1px solid #ddd;'>");
        emailBody.append(messageSource.getMessage("emailtemplate.common.lbl.footer", null, locale));
        emailBody.append("</div>");
        return emailBody.toString();
    }

    public static void main(String[] args) {
        //System.out.println(EmailTemplate.accountActivation("Agent of FreshFarms", "test@test.com", (short)4).getHtmlContent());
    }
}
