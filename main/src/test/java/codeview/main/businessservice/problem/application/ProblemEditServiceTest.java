package codeview.main.businessservice.problem.application;

import codeview.main.businessservice.member.infra.MemberRepository;
import codeview.main.businessservice.problem.domain.Problem;
import codeview.main.businessservice.problem.infra.repository.ProblemRepository;
import codeview.main.businessservice.problem.presentation.dao.ProblemCreateDao;
import codeview.main.businessservice.problemdescription.application.ProblemDescriptionService;
import codeview.main.businessservice.problemdescription.application.ProblemIoExampleService;
import codeview.main.businessservice.problemdescription.domain.ProblemDescription;
import codeview.main.businessservice.problemdescription.domain.ProblemIoExample;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProblemEditServiceTest {

    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    ProblemService problemService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProblemEditService problemEditService;

    @Autowired
    ProblemIoExampleService problemIoExampleService;

    @Autowired
    ProblemDescriptionService problemDescriptionService;

    static Problem staticProblem;
    static Long problemId;


    @Test
    public void 문제_편집_저장_테스트() throws Exception {
        //given
        problemEditService.editProblem(staticProblem,
                ProblemCreateDao.builder()
                        .problemName("test1213")
                        .build());
        problemRepository.flush();
        //when

        Problem problem = problemService.findById(staticProblem.getId());

        //then
        assertThat(problem.getName()).isEqualTo("test1213");

    }

    @Test
    public void 문제_io_예시_수정_개수_동일() throws Exception {
        //given
        List<String> inputs = Arrays.asList("testIn1", "testIn2");
        List<String> outputs = Arrays.asList("testOut1", "testOut2");

        ProblemCreateDao dao = ProblemCreateDao.builder().inputs(inputs).outputs(outputs).build();
        //when
        problemEditService.editProblemIoExample(staticProblem, dao);
        List<ProblemIoExample> problemIoExamples = problemIoExampleService.findAllByProblem(staticProblem);

        //then
        assertThat(problemIoExamples.size()).isEqualTo(2);
        assertThat(problemIoExamples.get(0).getInputSource()).isEqualTo("testIn1");
        assertThat(problemIoExamples.get(0).getOutputSource()).isEqualTo("testOut1");
        assertThat(problemIoExamples.get(0).getNumber()).isEqualTo(1);
        assertThat(problemIoExamples.get(1).getInputSource()).isEqualTo("testIn2");
        assertThat(problemIoExamples.get(1).getOutputSource()).isEqualTo("testOut2");
        assertThat(problemIoExamples.get(1).getNumber()).isEqualTo(2);

    }


    @Test
    public void 문제_io_예시_수정_개수_적음() throws Exception {
        //given
        List<String> inputs = Arrays.asList("testIn1");
        List<String> outputs = Arrays.asList("testOut1");

        ProblemCreateDao dao = ProblemCreateDao.builder().inputs(inputs).outputs(outputs).build();
        //when
        problemEditService.editProblemIoExample(staticProblem, dao);
        List<ProblemIoExample> problemIoExamples = problemIoExampleService.findAllByProblem(staticProblem);

        //then
        assertThat(problemIoExamples.size()).isEqualTo(1);
        assertThat(problemIoExamples.get(0).getInputSource()).isEqualTo("testIn1");
        assertThat(problemIoExamples.get(0).getOutputSource()).isEqualTo("testOut1");
        assertThat(problemIoExamples.get(0).getNumber()).isEqualTo(1);
    }

    @Test
    public void 문제_io_예시_수정_개수_많음() throws Exception {
        //given
        List<String> inputs = Arrays.asList("testIn1", "testIn2", "testIn3");
        List<String> outputs = Arrays.asList("testOut1", "testOut2", "testOut3");

        ProblemCreateDao dao = ProblemCreateDao.builder().inputs(inputs).outputs(outputs).build();
        //when
        problemEditService.editProblemIoExample(staticProblem, dao);
        List<ProblemIoExample> problemIoExamples = problemIoExampleService.findAllByProblem(staticProblem);

        //then
        assertThat(problemIoExamples.size()).isEqualTo(3);
        assertThat(problemIoExamples.get(0).getInputSource()).isEqualTo("testIn1");
        assertThat(problemIoExamples.get(0).getOutputSource()).isEqualTo("testOut1");
        assertThat(problemIoExamples.get(0).getNumber()).isEqualTo(1);
        assertThat(problemIoExamples.get(1).getInputSource()).isEqualTo("testIn2");
        assertThat(problemIoExamples.get(1).getOutputSource()).isEqualTo("testOut2");
        assertThat(problemIoExamples.get(1).getNumber()).isEqualTo(2);
        assertThat(problemIoExamples.get(2).getInputSource()).isEqualTo("testIn3");
        assertThat(problemIoExamples.get(2).getOutputSource()).isEqualTo("testOut3");
        assertThat(problemIoExamples.get(2).getNumber()).isEqualTo(3);

    }

    @Test
    public void 문제_ds_예시_수정_개수_동일() throws Exception {
        //given
        List<String> descriptions = Arrays.asList("testDes1", "testDes2");

        ProblemCreateDao dao = ProblemCreateDao.builder().descriptions(descriptions).build();
        //when
        problemEditService.editProblemDescription(staticProblem, dao);
        List<ProblemDescription> problemDescriptions = problemDescriptionService.findAllByProblem(staticProblem);

        //then
        assertThat(problemDescriptions.size()).isEqualTo(2);
        assertThat(problemDescriptions.get(0).getDescription()).isEqualTo("testDes1");
        assertThat(problemDescriptions.get(0).getNumber()).isEqualTo(1);
        assertThat(problemDescriptions.get(1).getDescription()).isEqualTo("testDes2");
        assertThat(problemDescriptions.get(1).getNumber()).isEqualTo(2);

    }


    @Test
    public void 문제_ds_예시_수정_개수_적음() throws Exception {
        //given
        List<String> descriptions = Arrays.asList("testDes1");

        ProblemCreateDao dao = ProblemCreateDao.builder().descriptions(descriptions).build();
        //when
        problemEditService.editProblemDescription(staticProblem, dao);
        List<ProblemDescription> problemDescriptions = problemDescriptionService.findAllByProblem(staticProblem);

        //then
        assertThat(problemDescriptions.size()).isEqualTo(1);
        assertThat(problemDescriptions.get(0).getDescription()).isEqualTo("testDes1");
        assertThat(problemDescriptions.get(0).getNumber()).isEqualTo(1);

    }

    @Test
    public void 문제_ds_예시_수정_개수_많음() throws Exception {
        //given
        List<String> descriptions = Arrays.asList("testDes1", "testDes2", "testDes3");

        ProblemCreateDao dao = ProblemCreateDao.builder().descriptions(descriptions).build();
        //when
        problemEditService.editProblemDescription(staticProblem, dao);
        List<ProblemDescription> problemDescriptions = problemDescriptionService.findAllByProblem(staticProblem);

        //then
        assertThat(problemDescriptions.size()).isEqualTo(3);
        assertThat(problemDescriptions.get(0).getDescription()).isEqualTo("testDes1");
        assertThat(problemDescriptions.get(0).getNumber()).isEqualTo(1);
        assertThat(problemDescriptions.get(1).getDescription()).isEqualTo("testDes2");
        assertThat(problemDescriptions.get(1).getNumber()).isEqualTo(2);
        assertThat(problemDescriptions.get(2).getDescription()).isEqualTo("testDes3");
        assertThat(problemDescriptions.get(2).getNumber()).isEqualTo(3);

    }




    @BeforeEach
    public void dataInit() {

        Problem problem = Problem
                .builder()
                .name("problem")
                .build();
        Problem saveProblem = problemRepository.save(problem);
        problemRepository.flush();

        staticProblem = problem;
        problemId = staticProblem.getId();

        ProblemIoExample io1 = ProblemIoExample.builder()
                .problem(problem)
                .number(1)
                .inputSource("in1")
                .outputSource("out1")
                .build();

        ProblemIoExample io2 = ProblemIoExample.builder()
                .problem(problem)
                .number(2)
                .inputSource("in2")
                .outputSource("out2")
                .build();

        problemIoExampleService.save(io1);
        problemIoExampleService.save(io2);
        problemIoExampleService.flush();

        ProblemDescription ds1 = ProblemDescription.builder()
                .problem(problem)
                .number(1)
                .description("test1")
                .build();

        ProblemDescription ds2 = ProblemDescription.builder()
                .problem(problem)
                .number(2)
                .description("test2")
                .build();

        problemDescriptionService.save(ds1);
        problemDescriptionService.save(ds2);
        problemDescriptionService.flush();


    }

}