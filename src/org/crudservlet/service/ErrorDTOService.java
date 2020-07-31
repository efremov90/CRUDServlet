package org.crudservlet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.crudservlet.dto.ErrorDTO;
import org.crudservlet.dto.ErrorResponseDTO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ErrorDTOService {
    public static void writer(HttpServletResponse resp, Exception e) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println(mapper.writeValueAsString(new ErrorResponseDTO(new ErrorDTO("1", e.getMessage(),
                e.toString() + "\n" +
                        Arrays.stream(e.getStackTrace()).map(x -> x.toString()).collect(Collectors.joining("\n"))))));
        out.close();
    }
}
