package gray.light.note.config;


import gray.light.book.config.BookAutoConfiguration;
import gray.light.note.handler.NoteQueryHandler;
import gray.light.note.handler.NoteUpdateHandler;
import gray.light.note.router.PersonalQueryNoteRouter;
import gray.light.note.router.PersonalUpdateNoteRouter;
import gray.light.note.service.ReadableNoteService;
import gray.light.note.service.WritableNoteService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Import;

@Import({
        NoteAutoConfiguration.ReadableNoteConfiguration.class,
        NoteAutoConfiguration.WritableNoteConfiguration.class
})
public class NoteAutoConfiguration {

    @Import({PersonalQueryNoteRouter. class, NoteQueryHandler.class, ReadableNoteService.class})
    public static class ReadableNoteConfiguration {

    }

    @ConditionalOnBean(BookAutoConfiguration.class)
    @Import({PersonalUpdateNoteRouter.class, NoteUpdateHandler.class, WritableNoteService.class})
    public static class WritableNoteConfiguration {

    }

}
