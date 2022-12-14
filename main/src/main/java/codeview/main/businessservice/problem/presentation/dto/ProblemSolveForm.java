package codeview.main.businessservice.problem.presentation.dto;

import codeview.main.businessservice.problem.domain.enumtype.ProblemLanguage;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ProblemSolveForm {

    @NotNull
    private ProblemLanguage problemLanguage;
    private String myPythonEditor;
    private String myJavaEditor;


}
