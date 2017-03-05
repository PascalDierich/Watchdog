package de.pascaldierich.model.domainmodels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * POJO for representing observable-entries in 'Observables' table
 *
 * @see {local.DOC_Watchdog.md}
 */
public final class Observable implements Parcelable {
    private boolean gotThumbnail;
    private int userId;
    private String displayName;
    private byte[] thumbnail;
    
    public boolean getGotThumbnail() {
        return gotThumbnail;
    }
    
    public Observable setGotThumbnail(boolean gotThumbnail) {
        this.gotThumbnail = gotThumbnail;
        return this;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public Observable setUserId(int userId) {
        this.userId = userId;
        return this;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public Observable setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }
    
    public byte[] getThumbnail() {
        return thumbnail;
    }
    
    public Observable setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }
    
    /*
        Parcelable
            -> created with http://www.parcelabler.com/
     */
    
    protected Observable(Parcel in) {
        gotThumbnail = in.readByte() != 0x00;
        userId = in.readInt();
        displayName = in.readString();
    }
    
    @Override
    public int describeContents() {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (gotThumbnail ? 0x01 : 0x00));
        dest.writeInt(userId);
        dest.writeString(displayName);
    }
    
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Observable> CREATOR = new Parcelable.Creator<Observable>() {
        @Override
        public Observable createFromParcel(Parcel in) {
            return new Observable(in);
        }
        
        @Override
        public Observable[] newArray(int size) {
            return new Observable[size];
        }
    };
}
