# No Void Structures
 Stops structures from generating in the void on floating islands style world generation.

Thank you @cputnam-a11y for the PR to update fabric to 1.21!

(see the `datapack` branch for the most recent version)

## What happened to the Mod?
The No Void Structures mod was one of my first Java modding projects, and because of that, it was quite frankly horrible (inflexible, incompatible, broke superflat structures, etc.). But that's okay! That's how we learn. and in all fairness, a lot of these issues boil down to Mojang's lack of modularity, so a seemingly "simple" project like just stopping structures from generating in the void, would actually involve ripping out *a lot* and introducing a better system entirely.

That aside, now that other projects like [Lithostitched](https://github.com/Apollounknowndev/lithostitched) exist, this mod is not needed as much. The main benefit is that this is data-driven, so customization for this is made a lot more friendly than just having all of the logic baked into a mod. Developers can use this pack and Lithostitched, and just edit some JSON files to get the exact behaviour they want.

That's why I have decided to change the implementation for No Void Structures to be a datapack *utilizing Lithostitched*, and that will be on the `datapack` branch.

I may make a No Void Structures 3.0.0 whenever I have the time and motivation to do so, but likely that would just be the cherry or sprinkles on a much larger project to address *all* of my gripes with Mojang's worldgen system. Which I quite frankly will probably never do, but its something I am heavily interested in.
