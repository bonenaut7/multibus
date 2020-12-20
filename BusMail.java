package by.fxg.multibus;

public abstract class BusMail {
	private boolean isCancelled;
	public abstract boolean isCancellable();
	
	public boolean isCancelled() {
		return this.isCancellable() && this.isCancelled;
	}
	
	public boolean setCancelled(boolean arg0) {
		if (this.isCancellable()) {
			this.isCancelled = arg0;
			return true;
		}
		return false;
	}
}
