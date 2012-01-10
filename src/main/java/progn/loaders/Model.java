package progn.loaders;

import progn.entity.ClimateZone;
import progn.entity.Sort;

import java.io.File;
import java.util.List;

/**
 * Created: 1/10/12 11:08 PM
 *
 * @author Alexei Novikov
 */
public class Model {

    private List<Sort> sorts;
    private List<String> sortDescriptions;
    private List<ClimateZone> zones;
    private List<String> zonesNames;

    public Model() {
        SortLoader sortLoader = new SortLoader(new File("src/main/resources", "sorts.txt"));
        sorts = sortLoader.getSorts();
        sortDescriptions = sortLoader.getSortDescriptions();

        ClimateLoader climateLoader = new ClimateLoader(new File("src/main/resources","zones.txt"));
        zones = climateLoader.getZones();
        zonesNames = climateLoader.getZonesNames();
    }

    public List<Sort> getSorts() {
        return sorts;
    }

    public List<String> getSortDescriptions() {
        return sortDescriptions;
    }

    public List<ClimateZone> getZones() {
        return zones;
    }

    public List<String> getZonesNames() {
        return zonesNames;
    }
}
