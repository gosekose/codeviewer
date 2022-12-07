package codeview.main.serverconnect.application.service;

import codeview.main.businessservice.problem.presentation.dto.ServerIoFilePathDto;
import codeview.main.serverconnect.presentation.dto.ServerIoFilePathResDto;
import codeview.main.serverconnect.presentation.dto.SolveRequestDto;
import codeview.main.serverconnect.presentation.dto.SolveResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

@Service
@Slf4j
@RequiredArgsConstructor
public class HttpConnectionService {

    private final ObjectMapper objectMapper;

    /**
     * 요청URL을 받아 통신하기
     * @param returnUrl
     * @return 응답결과
     * @throws Exception
     */
    public String httpURLConnection(String returnUrl , String parameterInfo, String charset, HttpServletRequest request) throws IOException {

        StringBuffer retStr = new StringBuffer();
        OutputStream os = null;
        BufferedWriter writer = null;
        BufferedReader br = null;

        try {
            URL url = new URL(returnUrl);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("POST");
            huc.setDoInput(true);
            huc.setDoOutput(true);
            huc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            huc.setConnectTimeout(2000); //상대방 서버 통신 오류로 인해 접속 지연시 강제로 timeout 처리;
            huc.setReadTimeout(2000); //상대방 서버 통신 오류로 인해 접속 지연시 강제로 timeout 처리;

            os = huc.getOutputStream();
            writer = new BufferedWriter(new OutputStreamWriter(os, charset));
            writer.write(parameterInfo);
            writer.flush(); // 버퍼를 비워준다.

            // 응답받은 메시지의 길이만큼 버퍼를 생성하여 읽어들임
            br = new BufferedReader(new InputStreamReader(huc.getInputStream(),charset));
            String data;
            // 표준출력으로 한 라인씩 출력
            data = br.readLine();
            while(data != null ) {
                if(retStr.length() > 0) {
                    retStr.append("\n");
                }
                retStr.append(data);
                data = br.readLine();
            }
        } catch (Exception e) {
            throw new IOException("요청URL을 받아 통신 처리하는 도중 예기치 않은 오류 발생");
        } finally{
            // 스트림을 닫는다.
            try {
                if(os != null){
                    os.close();
                }
            }catch(IOException e) {
                new IOException("OutputStream close 처리 도중 예기치 않은 오류 발생", e);
            }

            try {
                if(writer != null){
                    writer.close();
                }
            }catch(IOException e) {
                new IOException("BufferedWriter close 처리 도중 예기치 않은 오류 발생", e);
            }

            try {
                if(br != null){
                    br.close();
                }
            }catch(IOException e) {
                new IOException("BufferedReader close 처리 도중 예기치 않은 오류 발생", e);
            }
        }

        return retStr.toString();
    }

    //1.get방식 요청
    public SolveResponseDto requestSolveScore(SolveRequestDto solveRequestDto) throws JsonProcessingException {

        //URI를 빌드한다
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:5000")
                .path("/api/server/solve/test")
                .queryParam("problemUrl", solveRequestDto.getProblemUrl())
                .queryParam("solveRequestUrl", solveRequestDto.getSolveRequestUrl())
                .queryParam("solveId", solveRequestDto.getSolveId())
                .queryParam("score", solveRequestDto.getScore())
                .encode(Charset.defaultCharset())
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<SolveResponseDto> result = restTemplate.getForEntity(uri, SolveResponseDto.class);

        return result.getBody();
    }

    public ServerIoFilePathResDto requestProblemCreateTest(ServerIoFilePathDto serverIoFilePathDto) throws JsonProcessingException {

        //URI를 빌드한다
        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:5000")
                .path("/api/server/problem/demo/test")
                .queryParam("mainFilePath", serverIoFilePathDto.getMainFilePath())
                .queryParam("folderPath", serverIoFilePathDto.getIoFileDataDto().getFolderPath())
                .encode(Charset.defaultCharset())
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ServerIoFilePathResDto> result = restTemplate.getForEntity(uri, ServerIoFilePathResDto.class);

        return result.getBody();
    }
}
