package co.edu.unab.mgads.atenea.entity;

import java.io.Serializable;


public class LoginResponse implements Serializable {

    /*
    response {"sessid":"PSBNJimD3IAdWrXRXQE4cRJzovpdnkdxKz_0drzi_6o","session_name":"SESS48b6dad5828ad504d2cb7726169211ef","token":"5I-AY3iynvqBdVKh282QdqSi5TSE2jz6myU-m38A-kY","user":{"uid":"1","name":"admin","mail":"mauro1891@live.com","theme":"","signature":"","signature_format":null,"created":"1431881923","access":"1432788217","login":1432788669,"status":"1","timezone":"America/New_York","language":"","picture":null,"init":"mauro1891@live.com","data":false,"roles":{"2":"authenticated user","3":"administrator"},"rdf_mapping":{"rdftype":["sioc:UserAccount"],"name":{"predicates":["foaf:name"]},"homepage":{"predicates":["foaf:page"],"type":"rel"}}}}
    */

    private String sessid;
    private String session_name;
    private String token;

    private String cookie;
    private String headerCookie;

    public User user;
    public static class User implements Serializable {
        public String name;
        public String mail;
        public Picture picture;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMail() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public static class Picture implements Serializable{
            public String url;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public String getSessid() {
        return sessid;
    }

    public void setSessid(String sessid) {
        this.sessid = sessid;
    }

    public String getSession_name() {
        return session_name;
    }

    public void setSession_name(String session_name) {
        this.session_name = session_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCookie() {
        return this.sessid+"="+this.session_name;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getHeaderCookie() {
        return headerCookie;
    }

    public void setHeaderCookie(String headerCookie) {
        this.headerCookie = headerCookie;
    }


}






