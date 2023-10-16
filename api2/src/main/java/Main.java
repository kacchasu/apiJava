import com.fasterxml.jackson.core.JsonProcessingException;

public class Main {

    public static void main(String[] args) throws JsonProcessingException {
        System.out.println(CurrencyApi.getCurrencyList());
    }
}
