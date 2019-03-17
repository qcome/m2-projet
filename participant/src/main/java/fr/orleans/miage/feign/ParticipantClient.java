package fr.orleans.miage.feign;

import fr.orleans.miage.domain.Participant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("participant")
public interface ParticipantClient {

    @GetMapping(value = "/api/participant/soiree/{id}")
    List<Participant> getParticipantsBySoiree(@PathVariable(value = "idSoiree") Long idSoiree);

}
