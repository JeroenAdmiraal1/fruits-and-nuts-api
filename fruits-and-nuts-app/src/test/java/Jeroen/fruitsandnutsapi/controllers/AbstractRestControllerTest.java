package Jeroen.fruitsandnutsapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractRestControllerTest {
	public static String asJsonString(final Object o){
		try{
			return new ObjectMapper().writeValueAsString(o);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
