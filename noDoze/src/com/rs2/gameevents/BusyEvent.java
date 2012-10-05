package com.rs2.gameevents;

import com.rs2.model.Entity;
import com.rs2.model.combat.content.Poison;
import com.rs2.model.combat.ranged.Ranged;
import com.rs2.model.player.Client;

/**
 * 
 * @author killamess
 *
 */
public class BusyEvent {
	
	public static void busyEvent(Entity ent) {
		if (ent instanceof Client) {
			if (((Client)ent).foodDelay > 0) 
				((Client)ent).foodDelay--;
			
			if (((Client)ent).potionDelay > 0) 
				((Client)ent).potionDelay--;
			
			if (((Client)ent).chargeTimer > 0)
				((Client)ent).chargeTimer--;
			
			if (((Client)ent).projectileDelay > 0)
				((Client)ent).projectileDelay--;
			
			if (((Client)ent).chargeTimer >= 1) {
				((Client)ent).setCharged(true);
			} else {
				((Client)ent).setCharged(false);
			}
			if (((Client)ent).isViewingOrb()) {
				ent.setBusy(true);
			} else {
				ent.setBusy(false);
			}
			if (((Client)ent).poisonDelay > 0)
				((Client)ent).poisonDelay--;
		
			if (((Client)ent).poisonDamage > 0 && ((Client)ent).poisonDelay == 0) 
				Poison.poison((Client) ent);
			if (((Client)ent).projectileDelay == 1) {
				
				if (ent.getTarget() != null) {
					Ranged.createMSBProjectile(ent, ent.getTarget());
				}
			}
		} 
		
		if (ent.getBusyTimer() > 0) {
			
			if (ent instanceof Client) {
				((Client)ent).setBusy(true);
				((Client)ent).getActionSender().sendFlagRemoval();
			}
			
			ent.setBusy(true);
			ent.deductBusyTimer();

			if (ent.getBusyTimer() <= 0) {
				
				if (ent instanceof Client) {
					((Client)ent).setBusy(false);
				}
				ent.setBusy(false);	
			}
		}	
	}
}
