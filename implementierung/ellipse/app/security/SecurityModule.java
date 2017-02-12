// --------------------------------------------------------
// Code generated by Papyrus Java
// --------------------------------------------------------

package security;

import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.core.config.ConfigSingleton;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.play.ApplicationLogoutController;
import org.pac4j.play.CallbackController;
import org.pac4j.play.http.DefaultHttpActionAdapter;
import org.pac4j.play.store.PlayCacheStore;
import org.pac4j.play.store.PlaySessionStore;

import com.google.inject.AbstractModule;

import play.Configuration;
import play.Environment;

/************************************************************/
/**
 * Das SecurityModule ist eine von der Bibliothek pac4j vorgeschriebene Klasse,
 * welche die Bibliothek konfiguriert. Darin wird festgelegt, welche
 * Authentifizierungsmethoden verwendet werden sollen.
 */
public class SecurityModule extends AbstractModule {

    public SecurityModule(final Environment environment,
            final Configuration configuration) {
    }

    /**
     * Diese Methode wird von der Bibliothek aufgerufen und kreiert und
     * konfiguriert die Authentifizierungsmethoden.
     */
    @Override
    public void configure() {
        bind(PlaySessionStore.class).to(PlayCacheStore.class);

        FormClient formClient = new FormClient("/", new UserAuthenticator());
        formClient.setName("FormClient");

        Clients clients = new Clients("/callback", formClient);
        clients.init();

        Config config = new Config(clients);
        config.addAuthorizer("admin",
                new RequireAnyRoleAuthorizer<UserProfile>("ROLE_ADMIN"));
        config.addAuthorizer("adviser",
                new RequireAnyRoleAuthorizer<UserProfile>("ROLE_ADVISER"));
        config.addAuthorizer("student",
                new RequireAnyRoleAuthorizer<UserProfile>("ROLE_STUDENT"));
        config.addAuthorizer("studentOld",
                new RequireAnyRoleAuthorizer<UserProfile>("ROLE_STUDENT_OLD"));
        config.setHttpActionAdapter(new DefaultHttpActionAdapter());
        bind(Config.class).toInstance(config);

        // Setzt die Config in ein Singelton damit das UserManagement keine
        // NullpointerException wirft, da Dependency Injection nicht immer tut
        ConfigSingleton.setConfig(config);
        CallbackController callbackController = new CallbackController();
        callbackController.setDefaultUrl("/");
        callbackController.setConfig(config);
        callbackController.setMultiProfile(false);
        bind(CallbackController.class).toInstance(callbackController);
        ApplicationLogoutController logoutController = new ApplicationLogoutController();
        logoutController.setDefaultUrl("/");
        bind(ApplicationLogoutController.class).toInstance(logoutController);

    }
}
