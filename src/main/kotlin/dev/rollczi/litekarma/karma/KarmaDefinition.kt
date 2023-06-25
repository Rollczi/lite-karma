package dev.rollczi.litekarma.karma

import com.dzikoysk.sqiffy.definition.*
import com.dzikoysk.sqiffy.definition.ConstraintType.*
import com.dzikoysk.sqiffy.definition.DataType.*
import dev.rollczi.litekarma.LiteKarmaPluginVersion

@Definition([
    DefinitionVersion(
        version = LiteKarmaPluginVersion.V_0_1_0,
        name = "lite_karma",
        properties = [
            Property(name = "uuid", type = UUID_TYPE),
            Property(name = "karma", type = INT),
        ],
        constraints = [
            Constraint(type = PRIMARY_KEY, name = "pk_karma_uuid", on = ["uuid"]),
        ],
    )
])
internal object KarmaDefinition