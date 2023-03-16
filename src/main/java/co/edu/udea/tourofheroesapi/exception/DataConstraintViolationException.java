package co.edu.udea.tourofheroesapi.exception;

public class DataConstraintViolationException extends GeneralRuntimeException {
    private static  final long serialVersionUID = 1L;

    public DataConstraintViolationException(String mensaje){
        super(DataConstraintViolationException.class,mensaje);
    }

}
