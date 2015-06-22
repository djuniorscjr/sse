package module;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import repository.*;
import service.AlunoService;
import service.EtapaService;
import service.ProfessorService;
import service.UsuarioService;

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
        bind(UsuarioService.class).toProvider(new Provider<UsuarioService>() {
            @Override
            public UsuarioService get() {
                return new UsuarioService();
            }
        });
        bind(UsuarioRepository.class).toProvider(new Provider<UsuarioRepository>() {
            @Override
            public UsuarioRepository get() {
                return new UsuarioRepository();
            }
        });
        bind(EtapaService.class).toProvider(new Provider<EtapaService>() {
            @Override
            public EtapaService get() {
                return new EtapaService();
            }
        });
        bind(EtapaRepository.class).toProvider(new Provider<EtapaRepository>() {
            @Override
            public EtapaRepository get() {
                return new EtapaRepository();
            }
        });
    }
}
