package nl.utrecht.uni.questions.questions;

import android.os.AsyncTask;
import android.util.Log;

import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.TcpConnection;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;

import java.net.URL;

/**
 * Created by Gleb on 10/11/14.
 */
public class LabelPrinter extends AsyncTask<URL, Integer, String> {

    Connection conn;
    ZebraPrinter printer;
    String question;

    public LabelPrinter(String question) {
        this.question = question;
    }

    @Override
    protected String doInBackground(URL... params) {
        try {
            Log.e("LabelPrinter", "opening connection");
            conn = new TcpConnection("192.168.1.26", 80);
            conn.open();

            if ( conn.isConnected() ) {
                Log.e("LabelPrinter", "getting printer instance");
                printer = ZebraPrinterFactory.getInstance(conn);

                Log.e("LabelPrinter", "printing config label");
                printer.printConfigurationLabel();
                conn.close();
            }
        } catch (NumberFormatException e) {
            Log.e("LabelPrinter", "Port Number Is Invalid");
            return null;
        } catch (ConnectionException e) {
            Log.e("LabelPrinter", "Connection Exception");
        } catch (ZebraPrinterLanguageUnknownException e) {
            Log.e("LabelPrinter", "Zebra Printer Language Unknown Exception");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
