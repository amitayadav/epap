package com.auction.component;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auction.commons.util.AuctionLogger;
import com.auction.commons.util.DateHelper;
import com.auction.commons.util.InternetTiming;
import com.auction.model.bean.AnnouncementBean;
import com.auction.model.bean.AuctionTradesBean;
import com.auction.service.AnnouncementService;
import com.auction.service.AuctionTradesService;

@Component
public class AnnouncementHelper {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private AuctionTradesService tradesService;

    private static AuctionLogger logger = new AuctionLogger("AnnouncementHelper");

    /**
     * this method return announcementString
     *
     * @param announcementsDepth
     * @return
     */
    public String announcementString(Integer announcementsDepth) {
        Date startDateAnnouncement = DateHelper.decrementDateDay(InternetTiming.getInternetDateTime(), announcementsDepth);
        Date endDateDateAnnouncement = DateHelper.getDate(InternetTiming.getInternetDateTime(), false);
        return getAnnouncementString(announcementService.getAnnouncementsBetweenDate(startDateAnnouncement, endDateDateAnnouncement));
    }

    /**
     *
     * @param announcementBeanList
     * @return
     */
    public static String getAnnouncementString(List<AnnouncementBean> announcementBeanList) {
        logger.info("AnnouncementHelper Call getAnnouncementString method");
        StringBuilder announcementString = new StringBuilder();
        for (Iterator iterator = announcementBeanList.iterator(); iterator.hasNext();) {
            AnnouncementBean announcementBean = (AnnouncementBean) iterator.next();
            announcementBean.getAnnouncement();
            announcementString.append(announcementBean.getAnnouncement());

        }
        logger.info("=== Ending  getAnnouncementString method ===");
        return announcementString.toString();
    }

    /**
     * * this method return getAuctionTrades
     *
     * @param auctionTradeDepth
     * @return
     */
    public String getAuctionTrades(Integer auctionTradeDepth) {
        Date startDate = DateHelper.getDateBeforeHour(InternetTiming.getInternetDateTime(), auctionTradeDepth);
        Date endDate = DateHelper.getDate(InternetTiming.getInternetDateTime(), false);
        return getAuctionTradesString(tradesService.getAuctionTradesDate(startDate, endDate));
    }

    /**
     *
     * @param auctionTradesBeanLsit
     * @return
     */
    public static String getAuctionTradesString(List<AuctionTradesBean> auctionTradesBeanLsit) {
        StringBuilder auctionTradesBeanString = new StringBuilder();
        for (Iterator iterator = auctionTradesBeanLsit.iterator(); iterator.hasNext();) {
            AuctionTradesBean auctionTradesBean = (AuctionTradesBean) iterator.next();
            String auctionTradesInfo = auctionTradesBean.getId().getProductCatalogBean().getProductGroupName() + "-" + auctionTradesBean.getId().getProductCatalogBean().getProductName()
                    + "-" + auctionTradesBean.getId().getProductCatalogBean().getProductTypeName() + "&nbsp" + auctionTradesBean.getSoldQuantity() + "×" + String.format("%,.2f", auctionTradesBean.getSoldPrice()) + "ريال *** ";
            auctionTradesBeanString.append(auctionTradesInfo);

        }
        return auctionTradesBeanString.toString();
    }

}
