package umc.study.converter;


import umc.study.web.dto.TempResponse;

public class TempConverter {
    public static TempResponse.TempTestDTO toTempTest(){
        return TempResponse.TempTestDTO.builder()
                .testString("this is testing")
                .build();
    }

    public static TempResponse.TempExceptionDTO toTempExceptionTDTO(Integer flag){
        return TempResponse.TempExceptionDTO.builder()
                .flag(flag)
                .build();
    }
}
