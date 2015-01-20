package com.microsoft.bingads;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.easymock.EasyMock.*;

import java.net.MalformedURLException;
import java.net.URL;

import org.easymock.EasyMockRunner;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.junit.Test;
import org.junit.runner.RunWith;
import com.microsoft.bingads.OAuthDesktopMobileAuthCodeGrant;
import com.microsoft.bingads.OAuthTokens;
import com.microsoft.bingads.internal.oauth.OAuthRequestParameters;
import com.microsoft.bingads.internal.oauth.OAuthService;

@RunWith(EasyMockRunner.class)
public class OAuthAuthCodeForDesktopMobileAppTest extends EasyMockSupport {

    @Mock
    private OAuthService oauthService;

    @Mock
    private OAuthTokens expectedTokenInfo;

    @Test
    public void GetAuthorizationUrl_ReturnsCorrectUrl() {
        OAuthDesktopMobileAuthCodeGrant auth = CreateAuth("test_id");

        URL authorizationUrl = auth.getAuthorizationEndpoint();

        try {
            URL expectedUrl = new URL("https://login.live.com/oauth20_authorize.srf?"
                    + "scope=bingads.manage&"
                    + "response_type=code&"
                    + "redirect_uri=https%3A%2F%2Flogin.live.com%2Foauth20_desktop.srf&"
                    + "client_id=test_id"
            );
            assertEquals(expectedUrl, authorizationUrl);
        } catch (MalformedURLException e) {
            fail("Malformed Test URL");
        }
    }

    @Test
    public void RequestAccessAndRefreshTokensUsingResponseUri_ReturnsCorrectTokens() {
        try {
            OAuthRequestParameters expectedRequestParameters = new OAuthRequestParameters(
                    "test_id",
                    null,
                    new URL("https://login.live.com/oauth20_desktop.srf"),
                    "authorization_code",
                    "code",
                    "123"
            );

            expect(oauthService.getAccessTokens(expectedRequestParameters)).andReturn(expectedTokenInfo);

            OAuthDesktopMobileAuthCodeGrant auth = CreateAuth("test_id", oauthService);

            replayAll();

            OAuthTokens tokens = auth.requestAccessAndRefreshTokens(new URL("http://test.com/login?code=123"));

            assertEquals(expectedTokenInfo, tokens);

        } catch (MalformedURLException e) {
            fail("Malformed Test URL");
        }
    }

    @Test
    public void RequestAccessAndRefreshTokensUsingRefreshToken_ReturnsCorrectTokens() {
        try {
            expect(oauthService.getAccessTokens(new OAuthRequestParameters(
                    "test_id",
                    null,
                    new URL("https://login.live.com/oauth20_desktop.srf"),
                    "refresh_token",
                    "refresh_token",
                    "xxx"
            ))).andReturn(expectedTokenInfo);

            OAuthDesktopMobileAuthCodeGrant auth = CreateAuth("test_id", oauthService);

            replayAll();

            OAuthTokens tokens = auth.requestAccessAndRefreshTokens("xxx");

            assertEquals(expectedTokenInfo, tokens);

        } catch (MalformedURLException e) {
            fail("Malformed Test URL");
        }
    }

    private static OAuthDesktopMobileAuthCodeGrant CreateAuth(String clientId) {
        return new OAuthDesktopMobileAuthCodeGrant(clientId);
    }

    private static OAuthDesktopMobileAuthCodeGrant CreateAuth(String clientId, OAuthService service) {
        return new OAuthDesktopMobileAuthCodeGrant(clientId, service);
    }
}