package module;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import repository.*;
import service.*;

/**
 * Created by Domingos Junior on 01/06/2015.
 */
public class GlobalModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BasicRepository.class).toProvider(new Provider<BasicRepository>() {
            @Override
            public BasicRepository get() {
                return new BasicRepository();
            }
        });
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
        bind(ProjetoService.class).toProvider(new Provider<ProjetoService>() {
            @Override
            public ProjetoService get() {
                return new ProjetoService();
            }
        });
        bind(ProjetoRepository.class).toProvider(new Provider<ProjetoRepository>() {
            @Override
            public ProjetoRepository get() {
                return new ProjetoRepository();
            }
        });
        bind(RelatorioService.class).toProvider(new Provider<RelatorioService>() {
            @Override
            public RelatorioService get() {
                return new RelatorioService();
            }
        });
        bind(RelatorioRepository.class).toProvider(new Provider<RelatorioRepository>() {
            @Override
            public RelatorioRepository get() {
                return new RelatorioRepository();
            }
        });

        bind(AnexoService.class).toProvider(new Provider<AnexoService>() {
            @Override
            public AnexoService get() {
                return new AnexoService();
            }
        });
        bind(AnexoRepository.class).toProvider(new Provider<AnexoRepository>() {
            @Override
            public AnexoRepository get() {
                return new AnexoRepository();
            }
        });
    }
}
