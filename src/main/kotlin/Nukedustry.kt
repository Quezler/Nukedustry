import arc.Events
import arc.graphics.Color
import arc.math.Mathf
import arc.math.geom.Vec2
import arc.util.Time
import mindustry.Vars
import mindustry.content.Blocks
import mindustry.content.Fx
import mindustry.entities.Damage
import mindustry.entities.Effect
import mindustry.game.EventType
import mindustry.gen.Call
import mindustry.gen.Sounds
import mindustry.mod.Plugin
import mindustry.world.blocks.power.NuclearReactor

class Nukedustry : Plugin() {

    val vector = Vec2()

    override fun init() {
        Events.on(EventType.UnitDestroyEvent::class.java) {

            val unit = it.unit

            if (!unit.spawnedByCore) {
                val reactor = Blocks.thoriumReactor as NuclearReactor

                Sounds.explosionbig.at(unit)
                Call.effectReliable(Fx.nuclearShockwave, unit.x, unit.y, 0f, Color.acid)
                for (i in 0..5) {
                    Time.run(Mathf.random(40).toFloat()) { Call.effectReliable(Fx.nuclearcloud, unit.x, unit.y, 0f, Color.acid) }
                }

                Damage.damage(unit.x, unit.y, (reactor.explosionRadius * Vars.tilesize).toFloat(), (reactor.explosionDamage * 4).toFloat())

                for (i in 0..19) {
                    Time.run(Mathf.random(50).toFloat()) {
                        vector.rnd(Mathf.random(40f))
                        Call.effectReliable(Fx.explosion, vector.x + unit.x, vector.y + unit.y, 0f, Color.acid)
                    }
                }

                for (i in 0..69) {
                    Time.run(Mathf.random(80).toFloat()) {
                        vector.rnd(Mathf.random(120f))
                        Call.effectReliable(Fx.nuclearsmoke, vector.x + unit.x, vector.y + unit.y, 0f, Color.acid)
                    }
                }
            }
        }
    }
}