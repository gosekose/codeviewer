package codeview.main.membergroup.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.assertThat;

class MemberGroupCreateControllerTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    public void messageCodesResolverObject() throws Exception {
        //given
        String[] messages = codesResolver.resolveMessageCodes("required", "createClassesDto");

        //when

        //then
        assertThat(messages).containsExactly("required.createClassesDto", "required");

    }

    @Test
    public void messageCodesResolverField() throws Exception {
        //given
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "createClassesDto", "name", String.class);

        //when
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
        //then

        assertThat(messageCodes).containsExactly(
                "required.createClassesDto.name",
                "required.name",
                "required.java.lang.String",
                "required"
        );
    }

}