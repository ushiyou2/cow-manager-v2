package com.example.demo.gpt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.messagingstompwebsocket.MyService;
import com.example.demo.model.GPTRequest;
import com.example.demo.model.GPTResponse;
import com.example.demo.repository.GptRequestRepository;
import com.example.demo.repository.GptResponseRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ChatGPT {
	
	@Autowired
	private MyService myService;

    @Autowired
    private GptRequestRepository gptRequestRepository;

    @Autowired
    private GptResponseRepository gptResponseRepository;
	
    public String chatGPT(String text) throws Exception {
        String url = "https://api.openai.com/v1/chat/completions";
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + myService.getToken());
        
        GPTRequest.Message userMessage = new GPTRequest.Message();
        userMessage.setRole("user");
        userMessage.setContent(text);
        
        GPTRequest gptRequest = new GPTRequest();
        gptRequest.setModel("gpt-3.5-turbo");
        gptRequest.setMessages(List.of(userMessage));
        gptRequest.setN(1);
        gptRequest.setTemperature(0.5);
        gptRequest.setMax_tokens(30);
        gptRequest.setStop(List.of("\n"));
        
        //GPTへの質問リクエストをDBに保存
        gptRequest = gptRequestRepository.save(gptRequest);
        
        // GPTRequestインスタンスをJSON型に変換
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode data = objectMapper.valueToTree(gptRequest);
        
        con.setDoOutput(true);
        con.getOutputStream().write(data.toString().getBytes());

        int status = con.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String output = in.lines().reduce((a, b) -> a + b).get();
            
            //GPTからの返答を取得
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(output);
            String content = node.get("choices").get(0).get("message").get("content").asText();
            
            //取得した返答をGPTResponseに格納してDBに保存
            GPTResponse gptResponse = new GPTResponse();
            gptResponse.setContent(content);
            gptRequest.setResponse(gptResponse);
            gptResponse = gptResponseRepository.save(gptResponse);
            
            return content;
        } else {
            BufferedReader error = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            String errorOutput = error.lines().reduce((a, b) -> a + b).get();
            return errorOutput;
        }

    }
}
