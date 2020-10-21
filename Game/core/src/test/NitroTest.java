import com.backendboys.battlerace.model.gamemodel.player.Player;
import com.backendboys.battlerace.model.gamemodel.powerups.NitroPowerUp;
import com.backendboys.battlerace.model.gamemodel.world.GameWorld;
import com.backendboys.battlerace.model.gamemodel.world.ground.GroundStrategyFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NitroTest {

    private NitroPowerUp nitroPowerUp;
    private Player player;
    private GameWorld gameWorld;

    public NitroTest() {
        player = new Player("player");
        gameWorld = new GameWorld(GroundStrategyFactory.getSinCosStrategy(1000, 40, 5), 1);
        nitroPowerUp = new NitroPowerUp(gameWorld.getWorld(), 1, 1);
    }

    @Test
    public void testUsePowerUp() {
        player.addPowerUp(nitroPowerUp);
        assertEquals(1, player.getListPowerUp().size());

        player.addVehicle(gameWorld.getWorld(), 0, 0);

        float oldAcceleration = player.getVehicle().getAcceleration();

        player.usePowerUp();

        assertEquals(oldAcceleration * 1.2f, player.getVehicle().getAcceleration());

    }

}