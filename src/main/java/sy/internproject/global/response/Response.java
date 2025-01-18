package sy.internproject.global.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response<T> {
    private int status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public static <T> Response<T> of() {
        return new Response<>(200, "Success.", null);
    }

    public static <T> Response<T> of(int statusCode) {
        return new Response<>(statusCode, "Success.", null);
    }

    public static <T> Response<T> of(String message) {
        return new Response<>(200, message, null);
    }

    public static <T> Response<T> of(int statusCode, String message) {
        return new Response<>(statusCode, message, null);
    }
    public static <T> Response<T> of(String message, T data) {
        return new Response<>(200, message, data);
    }

    public static <T> Response<T> of(int statusCode, String message, T data) {
        return new Response<>(statusCode, message, data);
    }
}

