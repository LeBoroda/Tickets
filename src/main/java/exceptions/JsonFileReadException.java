package exceptions;

public class JsonFileReadException extends RuntimeException{
  public JsonFileReadException(String filePath, Exception e) {
    super(String.format("Reading file with path %s failed, please check the path.\\n Error message %s.", filePath, e));
  }
}
