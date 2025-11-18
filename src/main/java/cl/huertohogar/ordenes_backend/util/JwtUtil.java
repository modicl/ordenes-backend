package cl.huertohogar.ordenes_backend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    
    private String secretKey = "profesorsaavedraporfavorpongame-un-7-en-el-examenporfavorgracias"; // Esto debe estar en un .env , pero por razones academicas dejemoslo asi por ahora

    public Integer obtenerIdUsuarioDelToken(String token) {
        try {
            String tokenLimpio = token.replace("Bearer ", "");
            
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(tokenLimpio)
                    .getBody();

            // El ID del usuario está en el claim "sub" (subject)
            Object idUsuario = claims.get("sub");
            
            if (idUsuario == null) {
                throw new RuntimeException("El token no contiene el claim 'sub'");
            }

            return Integer.parseInt(idUsuario.toString());
            
        } catch (Exception e) {
            throw new RuntimeException("Token inválido o expirado: " + e.getMessage(), e);
        }
    }

    public String extractRol(String token) {
        try {
            String tokenLimpio = token.replace("Bearer ", "");
            
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey.getBytes())
                    .build()
                    .parseClaimsJws(tokenLimpio)
                    .getBody();

            Object rol = claims.get("rol");  // O el que uses
            
            if (rol == null) {
                throw new RuntimeException("El token no contiene el claim de rol");
            }

            return rol.toString();

        } catch (Exception e) {
            throw new RuntimeException("Error al extraer el rol del token: " + e.getMessage(), e);
        }
    }
}