package dnf.itemauction.domain.dnf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DnfService {

    @Value("${API-KEY}")
    private String API_KEY;
    private WebClient webClient;
    private Map<String, String> serverMap;
    private String URL = "https://api.neople.co.kr";

    public DnfService(){
        webClient = WebClient.builder().baseUrl(URL).build();
        initMap();
    }

    public String getCharacterInfo(String server, String characterName){
        String characterInfo = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/df/servers/")
                        .path(serverMap.get(server))
                        .path("/characters")
                        .queryParam("characterName",characterName)
                        .queryParam("apikey", API_KEY)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return characterInfo;
    }

    private void initMap() {
        serverMap = new HashMap<>();
        serverMap.put("카인","cain");
        serverMap.put("디레지에","diregie");
        serverMap.put("시로코","siroco");
        serverMap.put("프레이","prey");
        serverMap.put("카시야스","casillas");
        serverMap.put("힐더","hilder");
        serverMap.put("안톤","anton");
        serverMap.put("바칼","bakal");
    }
}



