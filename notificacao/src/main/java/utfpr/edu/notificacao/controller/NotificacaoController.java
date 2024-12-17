package utfpr.edu.notificacao.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import utfpr.edu.notificacao.service.NotificacaoService;

@RestController
@RequestMapping("/api/notificacao")
public class NotificacaoController {
    private final NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @GetMapping()
    public SseEmitter subscribe() {
        System.out.println("chesousa");
        return notificacaoService.subscribe();
    }
}
