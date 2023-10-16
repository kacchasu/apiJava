import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CurrencyApi {

    public static ResponseEntity<String> getCurrencyList() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.currencylayer.com/list";
        String apiKey = "e4f4142e546bc06d807d95e74d9c79d5";
        HttpHeaders headers = new HttpHeaders();
        headers.add("access_key", apiKey);

        HttpEntity<Object> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url + "?access_key=" + apiKey, HttpMethod.GET, null, String.class);

    }
}
