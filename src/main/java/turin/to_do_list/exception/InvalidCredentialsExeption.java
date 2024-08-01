package turin.to_do_list.exception;

public class InvalidCredentialsExeption extends RuntimeException{
    public InvalidCredentialsExeption(String message){
        super(message);
    }
}
