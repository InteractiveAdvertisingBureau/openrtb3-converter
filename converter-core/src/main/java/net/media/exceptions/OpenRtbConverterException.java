package net.media.exceptions;

/**
 * Exception Wrapper for OpenRtb converter.
 *
 *
 * @author shiva.b
 * @see java.lang.Exception
 * @since 1.0
 */
public class OpenRtbConverterException extends Exception {

  /**
   *
   *
   * @param message - error message
   * @param e - wrapped exception
   */
  public OpenRtbConverterException(String message, Exception e) {
    super(message, e);
  }

  public OpenRtbConverterException(Exception e) {
    super(e);
  }
}
