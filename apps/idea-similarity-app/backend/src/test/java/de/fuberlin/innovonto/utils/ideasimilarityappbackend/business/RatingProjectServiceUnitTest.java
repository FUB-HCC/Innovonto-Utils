package de.fuberlin.innovonto.utils.ideasimilarityappbackend.business;

import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.RatingProject;
import de.fuberlin.innovonto.utils.ideasimilarityappbackend.model.RatingProjectRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RatingProjectServiceUnitTest {


    @Test
    public void idsAreTrimmedBeforeSearchInRepo() {
        final RatingProjectRepository ratingProjectRepository = mock(RatingProjectRepository.class);
        final RatingProject mockProject = new RatingProject("mockId");
        when(ratingProjectRepository.findById("mockId")).thenReturn(Optional.of(mockProject));
        final RatingProjectService ratingProjectService = new RatingProjectService(ratingProjectRepository);

        final RatingProject resultForNormalInput = ratingProjectService.findById("mockId").get();
        assertThat(resultForNormalInput.getId()).isEqualTo("mockId");

        final RatingProject resultForInputWithWhitespace = ratingProjectService.findById("mockId ").get();
        assertThat(resultForInputWithWhitespace.getId()).isEqualTo("mockId");

        final Optional<RatingProject> resultForInputWithInnerWhitespace = ratingProjectService.findById("mock Id");
        assertThat(resultForInputWithInnerWhitespace).isEmpty();
    }
}