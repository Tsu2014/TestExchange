package com.tsu.test;


import com.sun.net.httpserver.HttpsExchange;
import com.sun.security.auth.NTSidUserPrincipal;
import com.tsu.test.reflect.Log;
import microsoft.exchange.webservices.data.EWSConstants;
import microsoft.exchange.webservices.data.autodiscover.IAutodiscoverRedirectionUrl;
import microsoft.exchange.webservices.data.autodiscover.exception.AutodiscoverLocalException;
import microsoft.exchange.webservices.data.core.EwsSSLProtocolSocketFactory;
import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.PropertySet;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BasePropertySet;
import microsoft.exchange.webservices.data.core.enumeration.property.WellKnownFolderName;
import microsoft.exchange.webservices.data.core.enumeration.search.FolderTraversal;
import microsoft.exchange.webservices.data.core.service.folder.CalendarFolder;
import microsoft.exchange.webservices.data.core.service.folder.Folder;
import microsoft.exchange.webservices.data.core.service.item.Appointment;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.core.service.item.Item;
import microsoft.exchange.webservices.data.core.service.schema.FolderSchema;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.EmailAddressCollection;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import microsoft.exchange.webservices.data.search.FindFoldersResults;
import microsoft.exchange.webservices.data.search.FindItemsResults;
import microsoft.exchange.webservices.data.search.FolderView;
import microsoft.exchange.webservices.data.search.ItemView;
import microsoft.exchange.webservices.data.search.filter.SearchFilter;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.registry.Registry;
import java.security.GeneralSecurityException;

public class TestExchange {

    private final static String SERVICE_HOST = "ex.qq.com";//"outlook.office365.com";//
    private static final String EMAIL_ADD = "tsu2021@qq.com";//"tsu202110@outlook.com";//
    private static final String PASSWORD = "isfknimvnodkbjij";//"SUsu7905";//"rprbsfgkrgpzbjdj";//
    private static final String DOMAIN = "qq.com";//"outlook.com";//
    private static final String EXCHANGE_VERSION = "";

    private static ExchangeService service;

    public static void main(String [] args){
        System.out.println("hello");
        //initExchangeService();
        try {
            //sendMessage();
            getMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initExchangeService(){
        service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);
        ExchangeCredentials credentials = new WebCredentials(EMAIL_ADD , PASSWORD);
        service.setCredentials(credentials);

        try {
            service.setUrl(new URI("https://"+SERVICE_HOST));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        service.setCredentials(credentials);
        service.setTraceEnabled(true);
    }

    public static void sendMessage() throws  Exception{
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
        ExchangeCredentials credentials = new WebCredentials(EMAIL_ADD, PASSWORD , DOMAIN);
        service.setCredentials(credentials);
        //service.autodiscoverUrl(EMAIL_ADD);
        service.setUrl(new URI("https://"+SERVICE_HOST+"/EWS/Exchange.asmx"));
        //service.setTraceEnabled(true);
        service.findItems(WellKnownFolderName.Inbox , new ItemView(10));
        EmailMessage emailMessage = new EmailMessage(service);
        emailMessage.setSubject("Test101302");
        emailMessage.setBody(MessageBody.getMessageBodyFromText("This test email 101302 !"));

        emailMessage.getToRecipients().add("l66566@126.com");
        emailMessage.send();
    }

    public static void getMessage() throws Exception{
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP1);
        ExchangeCredentials credentials = new WebCredentials(EMAIL_ADD, PASSWORD , DOMAIN);
        service.setCredentials(credentials);
        //service.autodiscoverUrl(EMAIL_ADD);
        service.setUrl(new URI("https://"+SERVICE_HOST+"/EWS/Exchange.asmx"));
        //service.setTraceEnabled(true);
        FindItemsResults<Item> result = service.findItems(WellKnownFolderName.Calendar , new ItemView(10));
        Log.d("result : "+result.getTotalCount());
        for(Item item : result){
            if(item instanceof Appointment){
                Appointment temp = (Appointment)item;
                Log.d("title : "+temp.getSubject()+" , start : "+temp.getStart()+" , end : "+temp.getEnd()+" , rrule : ");
            }else{
                Log.d("item : "+item.getSubject());
            }

        }
        //EmailAddressCollection collection = service.getRoomLists();
        //System.out.println("count : "+collection.getCount());
    }

    public static void sendMessage1() throws Exception{
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2010_SP2);

        ExchangeCredentials credentials = new WebCredentials(EMAIL_ADD, PASSWORD , DOMAIN);
        service.setCredentials(credentials);
        //service.autodiscoverUrl(EMAIL_ADD);
        service.setUrl(new URI("https://ex.qq.com"));
        service.setTraceEnabled(true);
        FolderView view = new FolderView(10);
        PropertySet set = new PropertySet(BasePropertySet.IdOnly);
        set.add(FolderSchema.DisplayName);
        view.setPropertySet(set);
        SearchFilter searchFilter = new SearchFilter.IsGreaterThan(FolderSchema.TotalCount, 0);
        view.setTraversal(FolderTraversal.Deep);
        EmailAddressCollection collection = service.getRoomLists();
        System.out.println("count : "+collection.getCount());
        FindFoldersResults findFolderResults = service.findFolders(WellKnownFolderName.Root, searchFilter, view);
        for (Folder folder : findFolderResults) {
            if (folder instanceof CalendarFolder) {
                System.out.println("Calendar folder: " + folder.getDisplayName());
                //calendarNames.add(folder.getDisplayName() + "," + folder.getId());
            }
        }
    }

}
