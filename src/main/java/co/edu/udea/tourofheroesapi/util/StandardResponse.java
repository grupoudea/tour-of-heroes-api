package co.edu.udea.tourofheroesapi.util;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Data
public class StandardResponse<T> {

    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private String status;
    private String message;
    private String dateTime;
    private T body;

    public StandardResponse() {
    }

    public StandardResponse(StatusStandardResponse status, T body){
        this.status = status.getStatus();
        this.dateTime = new SimpleDateFormat(DATE_TIME_FORMAT).format(Calendar.getInstance().getTime());
        this.body = body;
    }

    public StandardResponse(StatusStandardResponse status, String message){
        this.status = status.getStatus();
        this.dateTime = new SimpleDateFormat(DATE_TIME_FORMAT).format(Calendar.getInstance().getTime());
        this.message = message;
    }

    public StandardResponse(StatusStandardResponse status, String message, T body){
        this.status = status.getStatus();
        this.dateTime = new SimpleDateFormat(DATE_TIME_FORMAT).format(Calendar.getInstance().getTime());
        this.message = message;
        this.body = body;
    }

    public enum StatusStandardResponse {
        OK("CORRECT_TRANSACTION"),
        ERROR("INCORRECT_TRANSACTION");

        private String status;

        StatusStandardResponse(String status){
            this.status = status;
        }

        public String getStatus(){
            return status;
        }
    }

}
