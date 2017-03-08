/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example.avtalebok;

/**
 *
 * @author sveinbra
 */

import java.sql.*;

public abstract class ActiveDomainObject {
    public abstract void initialize (Connection conn);
    public abstract void refresh (Connection conn);
    public abstract void save (Connection conn);
}
