package pl.coderslab.userscrud.exceptions;

public class EmailDuplicateException extends RuntimeException {
  public EmailDuplicateException(String message) {
    super(message);
  }
}
