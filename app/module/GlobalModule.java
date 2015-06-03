package module;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import repository.AlunoRepository;
import repository.BasicRepository;
import repository.ProfessorRepository;
import service.AlunoService;
import service.ProfessorService;

/**
 * Created by Domingos Junior on 01/06/2015.
 */
public class GlobalModule extends AbstractModule {

    @Override
    protected void configure() {
         bind(AlunoService.class).toProvider(new Provider<AlunoService>() {
             @Override
             public AlunoService get() {
                 return new AlunoService();
             }
         });
        bind(AlunoRepository.class).toProvider(new Provider<AlunoRepository>() {
            @Override
            public AlunoRepository get() {
                return new AlunoRepository();
            }
        });
        bind(ProfessorService.class).toProvider(new Provider<ProfessorService>() {
            @Override
            public ProfessorService get() {
                return new ProfessorService();
            }
        });
        bind(ProfessorRepository.class).toProvider(new Provider<ProfessorRepository>() {
            @Override
            public ProfessorRepository get() {
                return new ProfessorRepository();
            }
        });
    }
}